package ru.itmo.soa.lab.storage.model.product.entity

import org.hibernate.annotations.Check
import org.hibernate.annotations.CreationTimestamp
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.transfer.entity.Transfer
import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

typealias ProductId = Int

@Entity
@Check(
    constraints = "trim(name) <> '' " +
            "and price > 0 " +
            "and length(part_number) between 25 and 48"
)
class Product(
    /**
     * Поле не может быть null,
     * Строка не может быть пустой
     */
    @field:NotBlank
    @field:Column(nullable = false)
    var name: String,

    /**
     * Поле не может быть null,
     * Значение поля должно быть больше 0
     */
    @field:Min(1)
    @field:NotNull
    @field:Column(nullable = false)
    var price: Int,

    /**
     * Длина строки не должна быть больше 48,
     * Значение этого поля должно быть уникальным,
     * Длина строки должна быть не меньше 25,
     * Поле может быть null
     */
    @field:Size(min = 25, max = 48)
    @field:Column(unique = true)
    var partNumber: String?,

    var manufactureCost: Float,

    /**
     * Поле может быть null
     */
    @field:Enumerated(EnumType.STRING)
    var unitOfMeasure: UnitOfMeasure?,

    /**
     * Поле может быть null
     */
    @field:ManyToOne(cascade = [CascadeType.REMOVE])
    var manufacturer: Organization?,
) {
    /**
     * Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @Min(1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: ProductId? = null

    /**
     * Поле не может быть null,
     * Значение этого поля должно генерироваться автоматически
     */
    @CreationTimestamp
    @Column(nullable = false)
    var creationDate: LocalDate? = null

    @ManyToMany(mappedBy = "products", cascade = [CascadeType.ALL])
    var transfers: List<Transfer> = listOf()
}
