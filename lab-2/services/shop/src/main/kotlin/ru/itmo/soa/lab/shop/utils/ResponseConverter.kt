package ru.itmo.soa.lab.shop.utils

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import javax.ws.rs.core.Response

suspend fun toResponse(response: HttpResponse): Response =
    Response
        .status(response.status.value)
        .entity(response.bodyAsText())
        .build()