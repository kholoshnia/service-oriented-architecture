package ru.itmo.soa.lab.storage.model.product.dto

import ru.itmo.soa.lab.storage.model.organization.dto.OrganizationDto
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.model.product.entity.UnitOfMeasure
import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ProductDto(
    /**
     * Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @field:Min(1)
    @field:NotNull
    val id: ProductId,

    /**
     * Поле не может быть null,
     * Значение этого поля должно генерироваться автоматически
     */
    @field:NotNull
    val creationDate: LocalDate,

    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @field:NotBlank
    val name: String,

    /**
     * Поле не может быть null
     */
    @field:NotNull
    val coordinates: CoordinatesDto,

    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0
     */
    @field:Min(1)
    @field:NotNull
    val price: Int,

    /**
     * Длина строки не должна быть больше 48,
     * Значение этого поля должно быть уникальным,
     * Длина строки должна быть не меньше 25,
     * Поле может быть null
     */
    @field:Size(min = 25, max = 48)
    val partNumber: String?,

    val manufactureCost: Float,

    /**
     * Поле может быть null
     */
    val unitOfMeasure: UnitOfMeasure?,

    /**
     * Поле может быть null
     */
    val manufacturer: OrganizationDto?,
)