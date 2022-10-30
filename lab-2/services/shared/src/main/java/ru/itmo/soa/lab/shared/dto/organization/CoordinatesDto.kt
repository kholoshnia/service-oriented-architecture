package ru.itmo.soa.lab.shared.dto.product

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@JsonRootName("coordinates")
data class CoordinatesDto(
    /**
     * Максимальное значение поля: 492,
     * Поле не может быть null
     */
    @field:Max(492)
    @field:NotNull
    var x: Double,

    /**
     * Значение поля должно быть больше -138,
     * Поле не может быть null
     */
    @field:Min(-139)
    @field:NotNull
    var y: Double,
)