package ru.itmo.soa.lab.shop.controllers

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Response
import javax.enterprise.context.RequestScoped

@Produces("application/json")
@Consumes("application/json")
@Path("/users")
@RequestScoped
class ShopController {
    @GET
    @Path("/all")
    fun getText(): Response = Response.ok("Hello world!").build()
}
