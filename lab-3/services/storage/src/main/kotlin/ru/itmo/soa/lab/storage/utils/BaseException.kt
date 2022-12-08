package ru.itmo.soa.lab.storage.utils

import org.springframework.http.HttpStatus

abstract class BaseException(
    override val message: String,
    val httpStatus: HttpStatus
) : Exception(message)
