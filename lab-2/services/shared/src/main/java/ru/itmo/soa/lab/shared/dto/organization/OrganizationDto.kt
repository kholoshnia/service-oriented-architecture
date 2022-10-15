package ru.itmo.soa.lab.shared.dto.organization

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@JsonRootName("organization")
class OrganizationDto(
    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @field:NotNull
    @field:Min(1)
    val id: Int,

    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @field:NotBlank
    @field:NotNull
    val name: String,

    /**
     * Длина строки не должна быть больше 1317,
     * Значение этого поля должно быть уникальным,
     * Поле может быть null
     */
    @field:Size(max = 1317)
    val fullName: String?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @field:Min(1)
    val annualTurnover: Int?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @field:Min(1)
    val employeesCount: Long?,
)
