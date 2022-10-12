package ru.itmo.soa.lab.storage.model.product.entity

import org.hibernate.annotations.Check
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Embeddable
@Check(
    constraints = "x <= 492 " +
            "and y >= -139"
)
class Coordinates(
    /**
     * Максимальное значение поля: 492,
     * Поле не может быть null
     */
    @field:Max(492)
    @field:NotNull
    @field:Column(nullable = false)
    var x: Int,

    /**
     * Значение поля должно быть больше -138,
     * Поле не может быть null
     */
    @field:Min(-139)
    @field:NotNull
    @field:Column(nullable = false)
    var y: Int,
)