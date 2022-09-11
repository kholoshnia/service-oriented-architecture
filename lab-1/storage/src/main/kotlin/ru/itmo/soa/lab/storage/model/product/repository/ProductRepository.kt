package ru.itmo.soa.lab.storage.model.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.model.product.entity.ProductId

interface ProductRepository : JpaRepository<Product, ProductId> {
    @Query(
        "select new ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto(" +
                "p.manufactureCost, " +
                "count(p.manufactureCost)) " +
                "from Product as p " +
                "group by p.manufactureCost"
    )
    fun getManufactureCostGroups(pageable: Pageable): Page<ManufactureCostGroupDto>

    fun getByPartNumberGreaterThan(partNumber: String, pageable: Pageable): Page<Product>
}