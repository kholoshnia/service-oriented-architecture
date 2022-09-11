package ru.itmo.soa.lab.storage.model.product.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class CoordinatesDto(
    /**
     * Максимальное значение поля: 492,
     * Поле не может быть null
     */
    @Max(492)
    @NotNull
    var x: Int,

    /**
     * Значение поля должно быть больше -138,
     * Поле не может быть null
     */
    @Min(-139)
    @NotNull
    var y: Int,
)