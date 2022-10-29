package ru.itmo.soa.lab.storage.services.organization

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.itmo.soa.lab.storage.model.organization.converter.OrganizationConverter
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository
import ru.itmo.soa.lab.storage.utils.OrganizationFilters
import ru.itmo.soa.lab.storage.utils.PageConverter

@Service
class OrganizationService(
    private val organizationRepository: OrganizationRepository,
    private val organizationConverter: OrganizationConverter,
    private val pageConverter: PageConverter
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
        return organizationRepository.save(newOrganization)
    }

    @Transactional
    fun deleteOrganization(organizationId: OrganizationId) =
        organizationRepository.deleteById(organizationId)

    fun getOrganizationsPage(organizationFilters: OrganizationFilters, pageable: Pageable) =
        pageConverter.toDto(organizationRepository.findOrganizationsPage(organizationFilters, pageable)
            .map { organizationConverter.toDto(it) })
}