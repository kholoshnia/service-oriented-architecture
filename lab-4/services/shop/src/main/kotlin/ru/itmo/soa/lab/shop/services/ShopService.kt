package ru.itmo.soa.lab.shop.services

import io.ktor.http.*
import org.springframework.stereotype.Service
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.utils.toResponse

@Service
class ShopService(
    private val storageClient: StorageClient
) {
    fun getProductsByManufacturer(
        manufacturerId: Int,
        queryParams: Parameters? = Parameters.Empty
    ) = storageClient.getProductsByManufacturer(manufacturerId, queryParams)

    fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        queryParams: Parameters? = Parameters.Empty
    ) = storageClient.getProductsByUnitOfMeasure(unitOfMeasure, queryParams)

    fun getProductsByManufacturerResponse(
        manufacturerId: Int,
        queryString: String?,
    ) = toResponse(getProductsByManufacturer(manufacturerId, queryString?.let { parseQueryString(it) }))

    fun getProductsByUnitOfMeasureResponse(
        unitOfMeasure: UnitOfMeasure,
        queryString: String?,
    ) = toResponse(getProductsByUnitOfMeasure(unitOfMeasure, queryString?.let { parseQueryString(it) }))
}
