package ru.itmo.soa.lab.storage.controller

import org.postgresql.util.PSQLException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.itmo.soa.lab.storage.services.product.ProductNotFoundException
import ru.itmo.soa.lab.storage.utils.AdviceUtil.errorResponse
import ru.itmo.soa.lab.storage.utils.BaseException
import javax.validation.ValidationException

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(BaseException::class)
    fun handleAuthException(e: BaseException) = errorResponse(e, e.httpStatus)

    @ExceptionHandler(ValidationException::class)
    fun handleBadRequest(e: ValidationException) = errorResponse(e, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(e: PSQLException) =
        errorResponse(e, HttpStatus.BAD_REQUEST) { it.serverErrorMessage?.detail }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(e: ProductNotFoundException) = errorResponse(e, HttpStatus.NOT_FOUND)
}
