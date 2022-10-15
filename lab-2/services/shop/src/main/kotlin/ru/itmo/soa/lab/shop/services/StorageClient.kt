package ru.itmo.soa.lab.shop.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.utils.PageableParams
import ru.itmo.soa.lab.shop.utils.addPageableParams
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class StorageClient {
    private val client = HttpClient(Apache) {
        install(Logging)
        defaultRequest {
            url(System.getenv("STORAGE_API_BASE_URL") ?: "http://localhost:8080/api/v1/")
        }
    }

    open suspend fun getProductsByManufacturer(
        manufacturerId: Int,
        pageableParams: PageableParams,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["manufacturerId"] = manufacturerId.toString()
            addPageableParams(pageableParams)
        }
    }

    open suspend fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        pageableParams: PageableParams,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["unitOfMeasure"] = unitOfMeasure.toString()
            addPageableParams(pageableParams)
        }
    }
}