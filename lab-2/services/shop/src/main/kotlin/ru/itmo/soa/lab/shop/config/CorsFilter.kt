package ru.itmo.soa.lab.shop.config

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.container.PreMatching
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@PreMatching
class CorsFilter : ContainerRequestFilter, ContainerResponseFilter {
    override fun filter(request: ContainerRequestContext) {
        if (isPreflightRequest(request)) {
            request.abortWith(Response.ok().build())
            return
        }
    }

    override fun filter(request: ContainerRequestContext, response: ContainerResponseContext) {
        if (request.getHeaderString("Origin") == null) {
            return
        }

        if (isPreflightRequest(request)) {
            response.headers.add("Access-Control-Allow-Credentials", "true")
            response.headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            response.headers.add(
                "Access-Control-Allow-Headers",
                "X-Requested-With, Authorization, Accept-Version, Content-MD5, CSRF-Token, Content-Type"
            )
        }

        response.headers.add("Access-Control-Allow-Origin", "*")
    }

    companion object {
        private fun isPreflightRequest(request: ContainerRequestContext): Boolean {
            return (request.getHeaderString("Origin") != null
                    && request.method.equals("OPTIONS", ignoreCase = true))
        }
    }
}
