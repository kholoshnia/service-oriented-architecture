package ru.itmo.soa.lab.storage.model.product.dto

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("manufactureCostGroup")
data class ManufactureCostGroupDto(
    val manufactureCost: Float,
    val count: Long
)
