package ru.itmo.soa.lab.storage.services.product

import org.springframework.http.HttpStatus
import ru.itmo.soa.lab.storage.utils.BaseException

class ProductNotFoundException(message: String = "Product was not found.") :
    BaseException(message, HttpStatus.NOT_FOUND)
