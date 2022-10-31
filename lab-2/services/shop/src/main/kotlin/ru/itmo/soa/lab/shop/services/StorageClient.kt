package ru.itmo.soa.lab.shop.services

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.http.parseQueryString
import org.apache.http.conn.ssl.NoopHostnameVerifier
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.shop.config.SslSettings
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
open class StorageClient {
    private val client = HttpClient(Apache) {
        install(Logging)
        defaultRequest {
            url(System.getenv("STORAGE_API_BASE_URL") ?: "")
        }
        engine {
            customizeClient {
                sslContext = SslSettings.getSslContext()
                setSSLHostnameVerifier(NoopHostnameVerifier())
            }
        }
    }

    open suspend fun getProductsByManufacturer(
        manufacturerId: Int,
        queryString: String?,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["manufacturerId"] = manufacturerId.toString()
            queryString?.let { parameters.appendAll(parseQueryString(queryString)) }
        }
    }

    open suspend fun getProductsByUnitOfMeasure(
        unitOfMeasure: UnitOfMeasure,
        queryString: String?,
    ) = client.get {
        url {
            appendPathSegments("products")
            parameters["unitOfMeasure"] = unitOfMeasure.toString()
            queryString?.let { parameters.appendAll(parseQueryString(queryString)) }
        }
    }
}