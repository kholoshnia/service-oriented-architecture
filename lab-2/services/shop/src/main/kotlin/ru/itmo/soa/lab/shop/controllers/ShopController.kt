package ru.itmo.soa.lab.shop.controllers

import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.services.ShopService
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.UriInfo

@Consumes("application/xml")
@Produces("application/xml")
@Path("/api/v1")
open class ShopController {
    @Inject
    private lateinit var shopService: ShopService

    @GET
    @Path("/filter/manufacturer/{manufacturerId}")
    open fun filterProductsByManufacturer(
        @PathParam("manufacturerId") manufacturerId: Int,
        @Context uriInfo: UriInfo
    ) = shopService.getProductsByManufacturer(manufacturerId, uriInfo.requestUri.query)

    @GET
    @Path("/filter/unit-of-measure/{unitOfMeasure}")
    open fun getProductsByUnitOfMeasure(
        @PathParam("unitOfMeasure") unitOfMeasure: UnitOfMeasure,
        @Context uriInfo: UriInfo
    ) = shopService.getProductsByUnitOfMeasure(unitOfMeasure, uriInfo.requestUri.query)
}
