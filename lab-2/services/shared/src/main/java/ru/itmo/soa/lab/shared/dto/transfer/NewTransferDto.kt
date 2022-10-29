package ru.itmo.soa.lab.shared.dto.transfer

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("newTransfer")
data class NewTransferDto(
    var products: List<Int>,
    var senderId: Int,
)