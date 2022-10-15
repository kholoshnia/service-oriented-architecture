package ru.itmo.soa.lab.storage.model.organization.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.organization.OrganizationDto
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class OrganizationConverter : DtoConverter<Organization, OrganizationDto> {
    override fun toDto(entity: Organization) = OrganizationDto(
        entity.id!!,
        entity.name,
        entity.fullName,
        entity.annualTurnover,
        entity.employeesCount
    )
}