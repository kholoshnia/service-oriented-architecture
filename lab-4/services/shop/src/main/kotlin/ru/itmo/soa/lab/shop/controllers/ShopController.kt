package ru.itmo.soa.lab.shop.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.services.ShopService
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/ebay/api/v1", produces = [MediaType.APPLICATION_XML_VALUE])
class ShopController(
    private var shopService: ShopService
) {
    @GetMapping("/filter/manufacturer/{manufacturerId}")
    fun filterProductsByManufacturer(
        request: HttpServletRequest,
        @PathVariable manufacturerId: Int,
    ) = shopService.getProductsByManufacturerResponse(manufacturerId, request.queryString)

    @GetMapping("/filter/unit-of-measure/{unitOfMeasure}")
    fun filterProductsByUnitOfMeasure(
        request: HttpServletRequest,
        @PathVariable unitOfMeasure: UnitOfMeasure,
    ) = shopService.getProductsByUnitOfMeasureResponse(unitOfMeasure, request.queryString)
}
