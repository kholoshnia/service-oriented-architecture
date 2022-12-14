package ru.itmo.soa.lab.storage.model.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.utils.ProductFilters

interface ProductRepository : JpaRepository<Product, ProductId> {
    @Query(
        "select p from Product as p where " +
                "(:#{#productFilters.id} is null or p.id = :#{#productFilters.id}) and " +
                "(:#{#productFilters.creationDate} is null or p.creationDate = :#{#productFilters.creationDate}) and " +
                "(:#{#productFilters.name} is null or p.name = :#{#productFilters.name}) and " +
                "(:#{#productFilters.coordinatesX} is null or p.coordinates.x = :#{#productFilters.coordinatesX}) and " +
                "(:#{#productFilters.coordinatesY} is null or p.coordinates.y = :#{#productFilters.coordinatesY}) and " +
                "(:#{#productFilters.price} is null or p.price = :#{#productFilters.price}) and " +
                "(:#{#productFilters.partNumber} is null or p.partNumber = :#{#productFilters.partNumber}) and " +
                "(:#{#productFilters.manufactureCost} is null or p.manufactureCost = :#{#productFilters.manufactureCost}) and " +
                "(:#{#productFilters.unitOfMeasure} is null or p.unitOfMeasure = :#{#productFilters.unitOfMeasure}) and " +
                "(:#{#productFilters.manufacturerId} is null or p.manufacturer.id = :#{#productFilters.manufacturerId}) and " +
                "(:#{#productFilters.manufacturerName} is null or p.manufacturer.name = :#{#productFilters.manufacturerName}) and " +
                "(:#{#productFilters.manufacturerFullName} is null or p.manufacturer.fullName = :#{#productFilters.manufacturerFullName}) and " +
                "(:#{#productFilters.manufacturerAnnualTurnover} is null or p.manufacturer.annualTurnover = :#{#productFilters.manufacturerAnnualTurnover}) and " +
                "(:#{#productFilters.manufacturerEmployeesCount} is null or p.manufacturer.employeesCount = :#{#productFilters.manufacturerEmployeesCount})"
    )
    fun findProductsPage(productFilters: ProductFilters, pageable: Pageable): Page<Product>

    @Query(
        "select new ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto(" +
                "p.manufactureCost, " +
                "count(p.manufactureCost)) " +
                "from Product as p " +
                "group by p.manufactureCost"
    )
    fun findManufactureCostGroups(pageable: Pageable): Page<ManufactureCostGroupDto>

    fun findByPartNumberGreaterThan(partNumber: String, pageable: Pageable): Page<Product>
}