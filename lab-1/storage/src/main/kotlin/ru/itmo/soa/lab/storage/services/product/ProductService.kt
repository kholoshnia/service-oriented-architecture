package ru.itmo.soa.lab.storage.services.product

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.itmo.soa.lab.storage.model.product.dto.NewProductDto
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.model.product.repository.ProductRepository
import ru.itmo.soa.lab.storage.utils.converter.impl.NewProductConverter
import ru.itmo.soa.lab.storage.utils.converter.impl.PageConverter
import ru.itmo.soa.lab.storage.utils.converter.impl.ProductConverter

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val newProductConverter: NewProductConverter,
    private val productConverter: ProductConverter,
    private val pageConverter: PageConverter
) {
    companion object {
        private val maxPricePageRequest = PageRequest.of(0, 1, Sort.by("price").descending())
    }

    @Transactional
    fun addProduct(newProductDto: NewProductDto) =
        productConverter.toDto(productRepository.save(newProductConverter.toEntity(newProductDto)))

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
        newProduct.creationDate = product.creationDate
        return productConverter.toDto(productRepository.save(newProduct))
    }

    @Transactional
    fun deleteProduct(productId: ProductId) {
        productRepository.deleteById(productId)
    }

    fun getProductsPage(
        sortBy: String,
        order: Sort.Direction,
        page: Int = 1,
        pageSize: Int = 10
    ) {
        // TODO implement
    }

    fun getMaxPriceProduct(): ProductDto =
        productRepository.findAll(maxPricePageRequest).stream()
            .findAny().map { productConverter.toDto(it) }
            .orElseThrow { ProductNotFoundException() }

    fun getManufactureCostGroups(pageable: Pageable) =
        pageConverter.toDto(productRepository.getManufactureCostGroups(pageable))

    fun getGreaterPartNumber(partNumber: String, pageable: Pageable) =
        productRepository.getByPartNumberGreaterThan(partNumber, pageable)
            .map { productConverter.toDto(it) }
}