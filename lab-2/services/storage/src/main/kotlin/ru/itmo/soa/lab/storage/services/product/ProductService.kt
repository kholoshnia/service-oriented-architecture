package ru.itmo.soa.lab.storage.services.product

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.itmo.soa.lab.shared.dto.product.NewProductDto
import ru.itmo.soa.lab.shared.dto.product.ProductDto
import ru.itmo.soa.lab.storage.model.product.converter.NewProductConverter
import ru.itmo.soa.lab.storage.model.product.converter.ProductConverter
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.model.product.repository.ProductRepository
import ru.itmo.soa.lab.storage.services.organization.OrganizationService
import ru.itmo.soa.lab.storage.utils.PageConverter
import ru.itmo.soa.lab.storage.utils.ProductFilters

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val newProductConverter: NewProductConverter,
    private val productConverter: ProductConverter,
    private val pageConverter: PageConverter,
    private val organizationService: OrganizationService,
) {
    companion object {
        private val maxPricePageRequest = PageRequest.of(0, 1, Sort.by("price").descending())
    }

    @Transactional
    fun addProduct(newProductDto: NewProductDto): ProductDto {
        val product = newProductConverter.toEntity(newProductDto)
        product.manufacturer = product.manufacturer?.let { organizationService.addOrganization(it) }
        return productConverter.toDto(productRepository.save(product))
    }

    fun getProductById(productId: ProductId): ProductDto =
        productRepository.findById(productId).map { productConverter.toDto(it) }
            .orElseThrow { ProductNotFoundException() }

    @Transactional
    fun updateProduct(
        productId: ProductId,
        newProductDto: NewProductDto
    ): ProductDto {
        val product = getProductById(productId)
        val newProduct = newProductConverter.toEntity(newProductDto)
        newProduct.id = product.id
        newProduct.creationDate = product.creationDate

        if (product.manufacturer == null && newProduct.manufacturer != null) {
            newProduct.manufacturer = organizationService.addOrganization(newProduct.manufacturer!!)
        } else if (product.manufacturer != null && newProduct.manufacturer != null) {
            newProduct.manufacturer =
                organizationService.updateOrganization(product.manufacturer!!.id, newProduct.manufacturer!!)
        } else if (product.manufacturer != null) {
            organizationService.deleteOrganization(product.manufacturer!!.id)
        }

        return productConverter.toDto(productRepository.save(newProduct))
    }

    @Transactional
    fun deleteProduct(productId: ProductId) {
        productRepository.deleteById(productId)
    }

    fun getProductsPage(productFilters: ProductFilters, pageable: Pageable) =
        pageConverter.toDto(productRepository.findProductsPage(productFilters, pageable)
            .map { productConverter.toDto(it) })

    fun getMaxPriceProduct(): ProductDto =
        productRepository.findAll(maxPricePageRequest).stream()
            .findAny().map { productConverter.toDto(it) }
            .orElseThrow { ProductNotFoundException() }

    fun getManufactureCostGroups(pageable: Pageable) =
        pageConverter.toDto(productRepository.findManufactureCostGroups(pageable))

    fun getGreaterPartNumber(partNumber: String, pageable: Pageable) =
        pageConverter.toDto(productRepository.findByPartNumberGreaterThan(partNumber, pageable)
            .map { productConverter.toDto(it) })
}