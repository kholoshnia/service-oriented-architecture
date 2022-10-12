package ru.itmo.soa.lab.storage.model.organization.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId

interface OrganizationRepository : JpaRepository<Organization, OrganizationId> {
}