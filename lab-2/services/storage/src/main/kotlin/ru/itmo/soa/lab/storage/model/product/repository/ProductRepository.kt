package ru.itmo.soa.lab.storage.model.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.itmo.soa.lab.shared.dto.product.ManufactureCostGroupDto
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.utils.ProductFilters

interface ProductRepository : JpaRepository<Product, ProductId> {
    @Query(
        "select p from Product as p " +
                "left join p.manufacturer pm where " +
                "(:#{#productFilters.id} is null or p.id = :#{#productFilters.id}) and " +
                "(:#{#productFilters.creationDate} is null or cast(p.creationDate as string) = :#{#productFilters.creationDate}) and " +
                "(:#{#productFilters.name} is null or p.name = :#{#productFilters.name}) and " +
                "(:#{#productFilters.price} is null or p.price = :#{#productFilters.price}) and " +
                "(:#{#productFilters.partNumber} is null or p.partNumber = :#{#productFilters.partNumber}) and " +
                "(:#{#productFilters.manufactureCost} is null or p.manufactureCost = :#{#productFilters.manufactureCost}) and " +
                "(:#{#productFilters.unitOfMeasure} is null or p.unitOfMeasure = :#{#productFilters.unitOfMeasure}) and " +
                "(:#{#productFilters.manufacturerId} is null or pm.id = :#{#productFilters.manufacturerId}) and " +
                "(:#{#productFilters.manufacturerName} is null or pm.name = :#{#productFilters.manufacturerName}) and " +
                "(:#{#productFilters.manufacturerFullName} is null or pm.fullName = :#{#productFilters.manufacturerFullName}) and " +
                "(:#{#productFilters.manufacturerAnnualTurnover} is null or pm.annualTurnover = :#{#productFilters.manufacturerAnnualTurnover}) and " +
                "(:#{#productFilters.manufacturerEmployeesCount} is null or pm.employeesCount = :#{#productFilters.manufacturerEmployeesCount}) and " +
                "(:#{#productFilters.manufacturerCoordinatesX} is null or pm.coordinates.x = :#{#productFilters.manufacturerCoordinatesX}) and " +
                "(:#{#productFilters.manufacturerCoordinatesY} is null or pm.coordinates.y = :#{#productFilters.manufacturerCoordinatesY})"
    )
    fun findProductsPage(productFilters: ProductFilters, pageable: Pageable): Page<Product>

    @Query(
        "select p from Product as p " +
                "left join p.manufacturer pm where " +
                "pm.id = :organizationId and (" +
                " select count(tp) from Transfer as t " +
                " left join t.products tp where tp.id = p.id and t.finishedAt is null) = 0 and " +
                "(:#{#productFilters.id} is null or p.id = :#{#productFilters.id}) and " +
                "(:#{#productFilters.creationDate} is null or cast(p.creationDate as string) = :#{#productFilters.creationDate}) and " +
                "(:#{#productFilters.name} is null or p.name = :#{#productFilters.name}) and " +
                "(:#{#productFilters.price} is null or p.price = :#{#productFilters.price}) and " +
                "(:#{#productFilters.partNumber} is null or p.partNumber = :#{#productFilters.partNumber}) and " +
                "(:#{#productFilters.manufactureCost} is null or p.manufactureCost = :#{#productFilters.manufactureCost}) and " +
                "(:#{#productFilters.unitOfMeasure} is null or p.unitOfMeasure = :#{#productFilters.unitOfMeasure}) and " +
                "(:#{#productFilters.manufacturerName} is null or pm.name = :#{#productFilters.manufacturerName}) and " +
                "(:#{#productFilters.manufacturerFullName} is null or pm.fullName = :#{#productFilters.manufacturerFullName}) and " +
                "(:#{#productFilters.manufacturerAnnualTurnover} is null or pm.annualTurnover = :#{#productFilters.manufacturerAnnualTurnover}) and " +
                "(:#{#productFilters.manufacturerEmployeesCount} is null or pm.employeesCount = :#{#productFilters.manufacturerEmployeesCount}) and " +
                "(:#{#productFilters.manufacturerCoordinatesX} is null or pm.coordinates.x = :#{#productFilters.manufacturerCoordinatesX}) and " +
                "(:#{#productFilters.manufacturerCoordinatesY} is null or pm.coordinates.y = :#{#productFilters.manufacturerCoordinatesY})"
    )
    fun findTransferProductsPage(
        productFilters: ProductFilters,
        organizationId: OrganizationId,
        pageable: Pageable
    ): Page<Product>

    @Query(
        "select new ru.itmo.soa.lab.shared.dto.product.ManufactureCostGroupDto(" +
                "p.manufactureCost, " +
                "count(p.manufactureCost)) " +
                "from Product as p " +
                "group by p.manufactureCost"
    )
    fun findManufactureCostGroups(pageable: Pageable): Page<ManufactureCostGroupDto>

    fun findByPartNumberGreaterThan(partNumber: String, pageable: Pageable): Page<Product>
}