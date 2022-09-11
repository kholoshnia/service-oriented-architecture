package ru.itmo.soa.lab.storage.model.product.dto

import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.model.product.entity.UnitOfMeasure
import java.time.LocalDate
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class ProductDto(
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
    @NotNull
    val creationDate: LocalDate,

    name: String,
    coordinates: CoordinatesDto,
    price: Int,
    partNumber: String?,
    manufactureCost: Float,
    unitOfMeasure: UnitOfMeasure?,
    manufacturer: Organization?,
) : NewProductDto(
    name,
    coordinates,
    price,
    partNumber,
    manufactureCost,
    unitOfMeasure,
    manufacturer
)