package ru.itmo.soa.lab.storage.services.transfer

import org.springframework.http.HttpStatus
import ru.itmo.soa.lab.storage.utils.BaseException

class TransferNotFoundException(message: String = "Transfer was not found.") :
    BaseException(message, HttpStatus.NOT_FOUND)

class TransferWithoutProductsException(message: String = "Cannot add transfer without products.") :
    BaseException(message, HttpStatus.BAD_REQUEST)

class TransferNotOwnerException(message: String = "Some products in transfer does not belong to the sender.") :
    BaseException(message, HttpStatus.BAD_REQUEST)

class AlreadyInTransferException(message: String = "Some products are already in transfer.") :
    BaseException(message, HttpStatus.BAD_REQUEST)