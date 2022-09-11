package ru.itmo.soa.lab.storage.model.organization.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class OrganizationDto(
    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @NotNull
    @Min(1)
    val id: Int,

    name: String,
    fullName: String?,
    annualTurnover: Int?,
    employeesCount: Long?,
) : NewOrganizationDto(
    name,
    fullName,
    annualTurnover,
    employeesCount
)