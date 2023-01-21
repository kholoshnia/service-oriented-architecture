package ru.itmo.soa.lab.shop.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.springframework.http.ResponseEntity

suspend fun toResponse(response: HttpResponse): ResponseEntity<*> =
    ResponseEntity
        .status(response.status.value)
        .body(response.bodyAsText())
