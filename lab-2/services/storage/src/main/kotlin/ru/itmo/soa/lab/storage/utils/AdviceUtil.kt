package ru.itmo.soa.lab.storage.utils

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

object AdviceUtil {
    fun <E : Exception> errorResponse(
        exception: E,
        status: HttpStatus,
        getMessage: (e: E) -> String?
    ): ResponseEntity<String?> =
        ResponseEntity
            .status(status)
            .contentType(MediaType.TEXT_PLAIN)
            .body(getMessage(exception))

    fun <T : Exception> errorResponse(exception: T, status: HttpStatus) =
        errorResponse(exception, status) { it.message }
}