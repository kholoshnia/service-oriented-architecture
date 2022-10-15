package ru.itmo.soa.lab.storage.services.organization

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository

@Service
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
) {
    @Transactional
    fun addOrganization(organization: Organization): Organization =
        organizationRepository.save(organization)

    fun getOrganizationById(organizationId: OrganizationId): Organization =
        organizationRepository.findById(organizationId)
            .orElseThrow { OrganizationNotFoundException() }

    @Transactional
    fun updateOrganization(organizationId: OrganizationId, newOrganization: Organization): Organization {
        val organization = getOrganizationById(organizationId)
        newOrganization.id = organization.id
        return organizationRepository.save(organization)
    }

    @Transactional
    fun deleteOrganization(organizationId: OrganizationId) =
        organizationRepository.deleteById(organizationId)
}