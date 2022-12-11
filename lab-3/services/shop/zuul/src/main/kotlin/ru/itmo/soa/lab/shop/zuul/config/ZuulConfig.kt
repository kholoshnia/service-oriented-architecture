package ru.itmo.soa.lab.shop.zuul.config

import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.cglib.proxy.*
import org.springframework.cloud.netflix.zuul.filters.RouteLocator
import org.springframework.cloud.netflix.zuul.web.ZuulController
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Method

@Configuration
class ZuulConfig {
    companion object {
        private const val ERROR_PATH = "/error"
        private const val METHOD = "lookupHandler"
    }

    @Bean
    fun zuulPostProcessor(
        @Autowired routeLocator: RouteLocator?,
        @Autowired zuulController: ZuulController?,
        @Autowired(required = false) errorController: ErrorController?
    ) = ZuulPostProcessor(routeLocator, zuulController, errorController)

    private enum class LookupHandlerCallbackFilter : CallbackFilter {
        INSTANCE;

        override fun accept(method: Method) = if (METHOD == method.name) 0 else 1
    }

    private enum class LookupHandlerMethodInterceptor : MethodInterceptor {
        INSTANCE;

        override fun intercept(target: Any, method: Method, args: Array<Any>, methodProxy: MethodProxy) =
            if (ERROR_PATH == args[0]) null else methodProxy.invokeSuper(target, args)
    }

    class ZuulPostProcessor(
        private val routeLocator: RouteLocator?,
        private val zuulController: ZuulController?,
        errorController: ErrorController?
    ) : BeanPostProcessor {
        private val hasErrorController: Boolean

        init {
            hasErrorController = errorController != null
        }

        @Throws(BeansException::class)
        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            if (hasErrorController && bean is ZuulHandlerMapping) {
                val enhancer = Enhancer()
                enhancer.setSuperclass(ZuulHandlerMapping::class.java)
                enhancer.setCallbackFilter(LookupHandlerCallbackFilter.INSTANCE)
                enhancer.setCallbacks(arrayOf(LookupHandlerMethodInterceptor.INSTANCE, NoOp.INSTANCE))
                val ctor = ZuulHandlerMapping::class.java.constructors[0]
                return enhancer.create(
                    ctor.parameterTypes, arrayOf(
                        routeLocator,
                        zuulController
                    )
                )
            }
            return bean
        }
    }
}


