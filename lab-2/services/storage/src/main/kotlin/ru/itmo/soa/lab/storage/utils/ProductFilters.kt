package ru.itmo.soa.lab.storage.utils

import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.product.entity.ProductId

data class ProductFilters(
    val id: ProductId? = null,
    val creationDate: String? = null,
    val name: String? = null,
    val price: Int? = null,
    val partNumber: String? = null,
    val manufactureCost: Float? = null,
    val unitOfMeasure: UnitOfMeasure? = null,
    val manufacturerId: OrganizationId? = null,
    val manufacturerName: String? = null,
    val manufacturerFullName: String? = null,
    val manufacturerAnnualTurnover: Int? = null,
    val manufacturerEmployeesCount: Long? = null,
    val manufacturerCoordinatesX: Double? = null,
    val manufacturerCoordinatesY: Double? = null,
)
