package ru.itmo.soa.lab.shared.dto.organization

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@JsonRootName("coordinates")
data class CoordinatesDto(
    @field:Min(0)
    @field:NotNull
    var x: Double,

    @field:Min(0)
    @field:NotNull
    var y: Double,
)