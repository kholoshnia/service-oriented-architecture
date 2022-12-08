package ru.itmo.soa.lab.storage.controller

import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.soa.lab.storage.services.organization.OrganizationService
import ru.itmo.soa.lab.storage.utils.OrganizationFilters

@RestController
@RequestMapping("/api/v1/organizations", produces = [MediaType.APPLICATION_XML_VALUE])
class OrganizationController(
    private val organizationService: OrganizationService
) {
    @GetMapping
    fun getOrganizationsPage(
        @ParameterObject pageable: Pageable,
        @ParameterObject organizationFilters: OrganizationFilters
    ) = organizationService.getOrganizationsPage(organizationFilters, pageable)
}
