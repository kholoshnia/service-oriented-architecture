package ru.itmo.soa.lab.storage.model.product.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import ru.itmo.soa.lab.storage.RepositoryUtil
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.utils.ProductFilters

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductRepositoryTest {
    @Autowired
    private lateinit var organizationRepository: OrganizationRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    private lateinit var repositoryUtil: RepositoryUtil

    private lateinit var product1: Product
    private lateinit var product2: Product

    @BeforeAll
    fun init() {
        repositoryUtil = RepositoryUtil(organizationRepository, productRepository)
    }

    @BeforeEach
    fun add() {
        product1 = repositoryUtil.saveProduct("test1")
        product2 = repositoryUtil.saveProduct("test2")
    }

    @AfterEach
    fun delete() {
        productRepository.deleteAll()
        organizationRepository.deleteAll()
    }

    @Test
    fun `should find all`() {
        assertThat(productRepository.findAll().map { it.id })
            .containsExactly(product1.id, product2.id)
    }

    @Test
    fun `should find products page`() {
        assertThat(productRepository.findProductsPage(
            ProductFilters(),
            PageRequest.of(0, 2)
        ).content.map { it.id }).containsExactly(product1.id, product2.id)
    }

    @Test
    fun `should find products page with id filter`() {
        assertThat(productRepository.findProductsPage(
            ProductFilters(id = product1.id),
            PageRequest.of(0, 2)
        ).content.map { it.id }).containsExactly(product1.id)
    }

    @Test
    fun `should find products page with name filter`() {
        assertThat(productRepository.findProductsPage(
            ProductFilters(name = product2.name),
            PageRequest.of(0, 2)
        ).content.map { it.id }).containsExactly(product2.id)
    }

    @Test
    fun `should find manufacture cost groups`() {
        val groups = productRepository.findManufactureCostGroups(PageRequest.of(0, 2)).content
        assertThat(groups.map { it.manufactureCost }).containsExactly(2.2f)
        assertThat(groups.map { it.count }).containsExactly(2L)
    }

    @Test
    fun `should find by part number greater than given part number`() {
        assertThat(productRepository.findByPartNumberGreaterThan(
            "test",
            PageRequest.of(0, 2)
        ).content.map { it.id }).containsExactly(product1.id, product2.id)
    }
}
