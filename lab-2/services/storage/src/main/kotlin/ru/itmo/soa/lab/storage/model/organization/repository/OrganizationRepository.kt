package ru.itmo.soa.lab.storage.model.organization.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.utils.OrganizationFilters

interface OrganizationRepository : JpaRepository<Organization, OrganizationId> {
    @Query(
        "select o from Organization as o where " +
                "(:#{#organizationFilters.id} is null or o.id = :#{#organizationFilters.id}) and " +
                "(:#{#organizationFilters.name} is null or o.name = :#{#organizationFilters.name}) and " +
                "(:#{#organizationFilters.fullName} is null or o.fullName = :#{#organizationFilters.fullName}) and " +
                "(:#{#organizationFilters.annualTurnover} is null or o.annualTurnover = :#{#organizationFilters.annualTurnover}) and " +
                "(:#{#organizationFilters.employeesCount} is null or o.employeesCount = :#{#organizationFilters.employeesCount}) and " +
                "(:#{#organizationFilters.coordinatesX} is null or o.coordinates.x = :#{#organizationFilters.coordinatesX}) and " +
                "(:#{#organizationFilters.coordinatesY} is null or o.coordinates.y = :#{#organizationFilters.coordinatesY})"
    )
    fun findOrganizationsPage(organizationFilters: OrganizationFilters, pageable: Pageable): Page<Organization>
}