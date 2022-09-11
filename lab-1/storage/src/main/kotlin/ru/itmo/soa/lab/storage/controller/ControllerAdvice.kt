package ru.itmo.soa.lab.storage.controller

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.itmo.soa.lab.storage.services.product.ProductNotFoundException
import ru.itmo.soa.lab.storage.utils.BaseException
import javax.validation.ValidationException

@RestControllerAdvice
class ControllerAdvice {
    private fun errorResponse(exception: Exception, status: HttpStatus) = ResponseEntity
        .status(status)
        .contentType(MediaType.TEXT_PLAIN)
        .body(exception.message)

    @ExceptionHandler(BaseException::class)
    fun handleAuthException(e: BaseException) = errorResponse(e, e.httpStatus)

    @ApiResponses(
        value = [ApiResponse(
            responseCode = "400", description = "Bad Request",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))]
        )]
    )
    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: ValidationException) = errorResponse(e, HttpStatus.BAD_REQUEST)

    @ApiResponses(
        value = [ApiResponse(
            responseCode = "404", description = "Not Found",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))]
        )]
    )
    @ExceptionHandler(ProductNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBookNotFound(e: ProductNotFoundException) = errorResponse(e, HttpStatus.NOT_FOUND)
}
