package ru.itmo.soa.lab.shop.endpoints

import io.ktor.client.call.*
import kotlinx.coroutines.runBlocking
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.config.WebServiceConfig
import ru.itmo.soa.lab.shop.services.ShopService
import ru.itmo.soa.lab.shop.soap.*
import ru.itmo.soa.lab.shop.utils.toParams

@Endpoint
class ShopEndpoint(
    private var shopService: ShopService
) {
    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getProductsByManufacturerRequest")
    @ResponsePayload
    fun getProductsByManufacturer(@RequestPayload request: GetProductsByManufacturerRequest): GetProductsByManufacturerResponse {
        val factory = ObjectFactory()
        val r = factory.createGetProductsByManufacturerResponse();
        r.page = 1;
        r.size = 2;
        r.total = 0;
        return r
    } /*= runBlocking {
        shopService.getProductsByManufacturer(
            request.manufacturerId,
            toParams(request.page, request.size, request.sort)
        ).body<GetProductsByManufacturerResponse>()
    }*/

    @PayloadRoot(namespace = WebServiceConfig.NAMESPACE_URI, localPart = "getProductsByUnitOfMeasureRequest")
    @ResponsePayload
    fun filterProductsByUnitOfMeasure(@RequestPayload request: GetProductsByUnitOfMeasureRequest) = runBlocking {
        shopService.getProductsByUnitOfMeasure(
            UnitOfMeasure.valueOf(request.unitOfMeasure.name),
            toParams(request.page, request.size, request.sort)
        ).body<GetProductsByUnitOfMeasureResponse>()
    }
}
