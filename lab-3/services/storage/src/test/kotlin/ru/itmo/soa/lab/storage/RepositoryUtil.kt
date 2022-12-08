package ru.itmo.soa.lab.storage

import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.repository.OrganizationRepository
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.model.product.repository.ProductRepository

class RepositoryUtil(
    private val organizationRepository: OrganizationRepository,
    private val productRepository: ProductRepository,
) {
    fun saveOrganization(uniqueName: String): Organization =
        organizationRepository.save(EntityUtil.createOrganization(uniqueName, false))

    fun saveProduct(uniqueName: String, price: Int = 1): Product {
        val product = EntityUtil.createProduct(uniqueName, false, price)
        product.manufacturer = product.manufacturer?.let(organizationRepository::save)
        return productRepository.saveAndFlush(product)
    }
}
