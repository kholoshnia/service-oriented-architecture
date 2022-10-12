package ru.itmo.soa.lab.storage.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.RepositoryUtil
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.model.product.repository.ProductRepository
import ru.itmo.soa.lab.storage.services.product.ProductService
import ru.itmo.soa.lab.storage.utils.ProductFilters

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {
    @Autowired
    private lateinit var productService: ProductService

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
        product1 = repositoryUtil.saveProduct("test1", 1)
        product2 = repositoryUtil.saveProduct("test2", 2)
    }

    @AfterEach
    fun delete() {
        productRepository.deleteAll()
        organizationRepository.deleteAll()
    }

    @Test
    fun `should add product`() {
        val product = DtoUtil.createNewProductDto("test3")
        assertThat(productService.addProduct(product).name)
            .isEqualTo(product.name)
    }

    @Test
    fun `should get product by id`() {
        assertThat(productService.getProductById(product1.id!!).id)
            .isEqualTo(product1.id)
    }

    @Test
    fun `should update product when manufacturer not null`() {
        val product = DtoUtil.createNewProductDto("test3")
        assertThat(productService.updateProduct(product1.id!!, product).name)
            .isEqualTo(product.name)
    }

    @Test
    fun `should update product when manufacturer was null`() {
        product1.manufacturer = null
        product1 = productRepository.save(product1)
        val product = DtoUtil.createNewProductDto("test3")
        assertThat(productService.updateProduct(product1.id!!, product).manufacturer)
            .isNotNull
    }

    @Test
    fun `should update product when manufacturer became null`() {
        val product = DtoUtil.createNewProductDto("test3", false)
        assertThat(productService.updateProduct(product1.id!!, product).manufacturer)
            .isNull()
    }

    @Test
    fun `should delete product`() {
        productService.deleteProduct(product1.id!!)
        assertThat(productRepository.findById(product1.id!!)).isEmpty
    }

    @Test
    fun `should get products page`() {
        assertThat(productService.getProductsPage(
            ProductFilters(),
            PageRequest.of(0, 2)
        ).data.map { it.id }).containsExactly(product1.id, product2.id)
    }

    @Test
    fun `should get products page with id filter`() {
        assertThat(productService.getProductsPage(
            ProductFilters(id = product1.id),
            PageRequest.of(0, 2)
        ).data.map { it.id }).containsExactly(product1.id)
    }

    @Test
    fun `should get products page with name filter`() {
        assertThat(productService.getProductsPage(
            ProductFilters(name = product2.name),
            PageRequest.of(0, 2)
        ).data.map { it.id }).containsExactly(product2.id)
    }

    @Test
    fun `should get max price product cost groups`() {
        assertThat(productService.getMaxPriceProduct().id)
            .isEqualTo(product2.id)
    }

    @Test
    fun `should get manufacture cost groups`() {
        val groups = productService.getManufactureCostGroups(PageRequest.of(0, 2)).data
        assertThat(groups.map { it.manufactureCost }).containsExactly(2.2f)
        assertThat(groups.map { it.count }).containsExactly(2L)
    }

    @Test
    fun `should get by part number greater than given part number`() {
        assertThat(productService.getGreaterPartNumber(
            "test",
            PageRequest.of(0, 2)
        ).content.map { it.id }).containsExactly(product1.id, product2.id)
    }
}