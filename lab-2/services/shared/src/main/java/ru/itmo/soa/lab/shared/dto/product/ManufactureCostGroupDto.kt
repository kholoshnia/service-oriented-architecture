package ru.itmo.soa.lab.shared.dto.product

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("manufactureCostGroup")
data class ManufactureCostGroupDto(
    val manufactureCost: Float,
    val count: Long
)
