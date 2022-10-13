package ru.itmo.soa.lab.storage.model.product.dto

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
    var x: Int,

    /**
     * Значение поля должно быть больше -138,
     * Поле не может быть null
     */
    @field:Min(-139)
    @field:NotNull
    var y: Int,
)