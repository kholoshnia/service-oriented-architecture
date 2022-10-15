package ru.itmo.soa.lab.storage.model.organization.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.organization.NewOrganizationDto
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class NewOrganizationConverter : DtoConverter<Organization, NewOrganizationDto> {
    override fun toEntity(dto: NewOrganizationDto) = Organization(
        dto.name,
        dto.fullName,
        dto.annualTurnover,
        dto.employeesCount
    )
}