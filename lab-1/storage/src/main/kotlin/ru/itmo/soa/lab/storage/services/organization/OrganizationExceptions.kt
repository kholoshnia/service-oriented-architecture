package ru.itmo.soa.lab.storage.services.organization

import org.springframework.http.HttpStatus
import ru.itmo.soa.lab.storage.utils.BaseException

class OrganizationNotFoundException(message: String = "Organization was not found.") :
    BaseException(message, HttpStatus.NOT_FOUND)