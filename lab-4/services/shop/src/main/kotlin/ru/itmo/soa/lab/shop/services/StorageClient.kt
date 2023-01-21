package ru.itmo.soa.lab.shop.services

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.config.SslSettings
import java.net.URI

@Service
class StorageClient(
    @Value("\${config.storage-api-base-url}")
    private val storageApiBaseUrl: String,
    private val sslSettings: SslSettings
) {
    private val client = HttpClient(Apache) {
        install(Logging)
        defaultRequest {
            url(URI(storageApiBaseUrl).toString().plus("/"))
        }
        engine {
            customizeClient {
                sslContext = sslSettings.getSslContext()
                setSSLHostnameVerifier(NoopHostnameVerifier())
            }
        }
    }

    suspend fun getProductsByManufacturer(
        manufacturerId: Int,
        queryString: String?,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["manufacturerId"] = manufacturerId.toString()
            queryString?.let { parameters.appendAll(parseQueryString(it)) }
        }
    }

    suspend fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        queryString: String?,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["unitOfMeasure"] = unitOfMeasure.toString()
            queryString?.let { parameters.appendAll(parseQueryString(it)) }
        }
    }
}
