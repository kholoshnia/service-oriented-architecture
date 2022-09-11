package ru.itmo.soa.lab.storage.model.organization.entity

import org.hibernate.annotations.Check
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

typealias OrganizationId = Int

@Entity
@Check(
    constraints = "trim(name) <> '' " +
            "and length(full_name) <= 1317 " +
            "and annual_turnover >= 1 " +
            "and employees_count >= 1"
)
class Organization(
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @NotBlank
    @NotNull
    @Column(nullable = false)
    var name: String,

    /**
     * Длина строки не должна быть больше 1317,
     * Значение этого поля должно быть уникальным,
     * Поле может быть null
     */
    @Size(max = 1317)
    @Column(unique = true)
    var fullName: String?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @Min(1)
    var annualTurnover: Int?,

    /**
     * Поле может быть null,
     * Значение поля должно быть больше 0
     */
    @Min(1)
    var employeesCount: Long?,
) {
    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @NotNull
    @Min(1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: OrganizationId? = null
}