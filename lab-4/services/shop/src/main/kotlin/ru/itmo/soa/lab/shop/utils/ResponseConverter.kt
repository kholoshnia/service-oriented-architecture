package ru.itmo.soa.lab.shop.utils

import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity

fun toResponse(response: HttpResponse): ResponseEntity<*> = runBlocking {
    ResponseEntity
        .status(response.status.value)
        .body(response.bodyAsText())
}
