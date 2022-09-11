package ru.itmo.soa.lab.storage.model.organization.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class NewOrganizationDto(
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @NotBlank
    @NotNull
    val name: String,

    /**
     * Длина строки не должна быть больше 1317,
     * Значение этого поля должно быть уникальным,
     * Поле может быть null
     */
    @Size(max = 1317)
    val fullName: String?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @Min(1)
    val annualTurnover: Int?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @Min(1)
    val employeesCount: Long?,
)