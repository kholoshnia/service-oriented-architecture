package ru.itmo.soa.lab.storage.utils

import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId

data class OrganizationFilters(
    val id: OrganizationId? = null,
    val name: String? = null,
    val fullName: String? = null,
    val annualTurnover: Int? = null,
    val employeesCount: Long? = null,
    val coordinatesX: Double? = null,
    val coordinatesY: Double? = null,
)
