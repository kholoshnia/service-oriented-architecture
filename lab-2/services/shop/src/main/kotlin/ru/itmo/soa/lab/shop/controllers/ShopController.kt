package ru.itmo.soa.lab.shop.controllers

import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.services.ShopService
import ru.itmo.soa.lab.shop.utils.PageableParams
import javax.inject.Inject
import javax.ws.rs.BeanParam
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

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
        @BeanParam pageableParams: PageableParams,
    ) = shopService.getProductsByManufacturer(manufacturerId, pageableParams)

    @GET
    @Path("/filter/unity-of-measure/{unitOfMeasure}")
    open fun getProductsByUnitOfMeasure(
        @PathParam("unitOfMeasure") unitOfMeasure: UnitOfMeasure,
        @BeanParam pageableParams: PageableParams,
    ) = shopService.getProductsByUnitOfMeasure(unitOfMeasure, pageableParams)
}
