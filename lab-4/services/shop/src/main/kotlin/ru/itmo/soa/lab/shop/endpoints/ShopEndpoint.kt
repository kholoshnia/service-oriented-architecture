package ru.itmo.soa.lab.shop.endpoints

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.config.WebServiceConfig
import ru.itmo.soa.lab.shop.services.ShopService
import ru.itmo.soa.lab.shop.soap.GetProductsByManufacturerRequest
import ru.itmo.soa.lab.shop.soap.GetProductsByManufacturerResponse
import ru.itmo.soa.lab.shop.soap.GetProductsByUnitOfMeasureRequest
import ru.itmo.soa.lab.shop.soap.GetProductsByUnitOfMeasureResponse
import ru.itmo.soa.lab.shop.utils.toParams

@Endpoint
class ShopEndpoint(
    private val shopService: ShopService,
) {
    private val xmlMapper = XmlMapper()

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getProductsByManufacturerRequest")
    @ResponsePayload
    fun filterProductsByManufacturer(@RequestPayload request: GetProductsByManufacturerRequest) = runBlocking {
        xmlMapper.readValue(
            shopService.getProductsByManufacturer(
                request.manufacturerId,
                toParams(request.page, request.size, request.sort)
            ).bodyAsText(), GetProductsByManufacturerResponse::class.java
        )
    }

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getProductsByUnitOfMeasureRequest")
    @ResponsePayload
    fun filterProductsByUnitOfMeasure(@RequestPayload request: GetProductsByUnitOfMeasureRequest) = runBlocking {
        xmlMapper.readValue(
            shopService.getProductsByUnitOfMeasure(
                UnitOfMeasure.valueOf(request.unitOfMeasure.name),
                toParams(request.page, request.size, request.sort)
            ).bodyAsText(), GetProductsByUnitOfMeasureResponse::class.java
        )
    }
}
