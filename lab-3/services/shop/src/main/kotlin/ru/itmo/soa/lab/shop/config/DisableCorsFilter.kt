package ru.itmo.soa.lab.shop.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class DisableCorsFilter : Filter {
    override fun init(filterConfig: FilterConfig?) { /* no-op */ }

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse

        response.setHeader("Access-control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS, POST, PUT, PATCH, DELETE")
        response.setHeader("Access-Control-Allow-Headers", "*")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Credentials", "true")

        if (!request.method.equals("OPTIONS", ignoreCase = true)) {
            chain.doFilter(request, response)
        } else {
            response.status = HttpServletResponse.SC_OK
        }
    }

    override fun destroy() { /* no-op */ }
}
