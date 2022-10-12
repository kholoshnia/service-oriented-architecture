package ru.itmo.soa.lab.storage.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.EntityUtil
import ru.itmo.soa.lab.storage.RepositoryUtil
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository
import ru.itmo.soa.lab.storage.model.product.repository.ProductRepository
import ru.itmo.soa.lab.storage.services.organization.OrganizationService

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrganizationServiceTest {
    @Autowired
    private lateinit var organizationService: OrganizationService

    @Autowired
    private lateinit var organizationRepository: OrganizationRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    private lateinit var repositoryUtil: RepositoryUtil

    private lateinit var organization1: Organization
    private lateinit var organization2: Organization

    @BeforeAll
    fun init() {
        repositoryUtil = RepositoryUtil(organizationRepository, productRepository)

    }

    @BeforeEach
    fun add() {
        organization1 = repositoryUtil.saveOrganization("test1")
        organization2 = repositoryUtil.saveOrganization("test2")
    }

    @AfterEach
    fun delete() {
        organizationRepository.deleteAll()
    }

    @Test
    fun `should add organization`() {
        val organization = EntityUtil.createOrganization("test3", false)
        assertThat(organizationService.addOrganization(organization).id).isNotNull
    }

    @Test
    fun `should get organization by id`() {
        assertThat(organizationService.getOrganizationById(organization1.id!!).id)
            .isEqualTo(organization1.id)
    }

    @Test
    fun `should update organization`() {
        val organization = EntityUtil.createOrganization("test3", false)
        assertThat(organizationService.updateOrganization(organization1.id!!, organization).name)
            .isEqualTo(organization.name)
    }
}