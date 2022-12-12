package ru.itmo.soa.lab.shop.services

import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.utils.toResponse

@Service
class ShopService(
    private val storageClient: StorageClient
) {
    fun getProductsByManufacturer(
        manufacturerId: Int,
        queryString: String?,
    ) = runBlocking {
        return@runBlocking toResponse(storageClient.getProductsByManufacturer(manufacturerId, queryString))
    }

    fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        queryString: String?,
    ) = runBlocking {
        return@runBlocking toResponse(storageClient.getProductsByUnitOfMeasure(unitOfMeasure, queryString))
    }
}
