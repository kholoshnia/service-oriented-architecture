package ru.itmo.soa.lab.storage.utils.converter.impl

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.storage.model.organization.dto.NewOrganizationDto
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.utils.converter.DtoConverter

@Component
class NewOrganizationConverter : DtoConverter<Organization, NewOrganizationDto> {
    override fun toEntity(dto: NewOrganizationDto) = Organization(
        dto.name,
        dto.fullName,
        dto.annualTurnover,
        dto.employeesCount
    )
}