package ru.itmo.soa.lab.shop.services

import kotlinx.coroutines.runBlocking
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.utils.toResponse
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class ShopService {
    @Inject
    private lateinit var storageClient: StorageClient

    open fun getProductsByManufacturer(
        manufacturerId: Int,
        queryString: String?,
    ) = runBlocking {
        return@runBlocking toResponse(storageClient.getProductsByManufacturer(manufacturerId, queryString))
    }

    open fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        queryString: String?,
    ) = runBlocking {
        return@runBlocking toResponse(storageClient.getProductsByUnitOfMeasure(unitOfMeasure, queryString))
    }
}