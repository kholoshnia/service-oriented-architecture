package ru.itmo.soa.lab.storage.model.product.dto

import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.product.entity.UnitOfMeasure
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class NewProductDto(
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @NotBlank
    val name: String,

    /**
     * Поле не может быть null
     */
    @NotNull
    val coordinates: CoordinatesDto,

    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0
     */
    @Min(1)
    @NotNull
    val price: Int,

    /**
     * Длина строки не должна быть больше 48,
     * Значение этого поля должно быть уникальным,
     * Длина строки должна быть не меньше 25,
     * Поле может быть null
     */
    @Size(min = 25, max = 48)
    val partNumber: String?,

    val manufactureCost: Float,

    /**
     * Поле может быть null
     */
    val unitOfMeasure: UnitOfMeasure?,

    /**
     * Поле может быть null
     */
    val manufacturer: Organization?,
)