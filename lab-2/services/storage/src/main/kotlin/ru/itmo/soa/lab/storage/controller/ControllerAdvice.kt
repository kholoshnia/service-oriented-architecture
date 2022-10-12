package ru.itmo.soa.lab.storage.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
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

    @ExceptionHandler(ValidationException::class)
    fun handleBadRequest(e: ValidationException) = errorResponse(e, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleBookNotFound(e: ProductNotFoundException) = errorResponse(e, HttpStatus.NOT_FOUND)
}
