package ru.itmo.soa.lab.storage.model.transfer.entity

import org.hibernate.annotations.CreationTimestamp
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.product.entity.Coordinates
import ru.itmo.soa.lab.storage.model.product.entity.Product
import java.time.LocalDateTime
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

typealias TransferId = Int

@Entity
class Transfer(
    @field:Embedded
    var coordinates: Coordinates,

    @field:ManyToMany
    var products: List<Product>,

    @field:ManyToOne(optional = false)
    var sender: Organization,

    @field:ManyToOne
    var receiver: Organization? = null,

    var finishedAt: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: TransferId? = null

    @CreationTimestamp
    var startedAt: LocalDateTime? = null
}