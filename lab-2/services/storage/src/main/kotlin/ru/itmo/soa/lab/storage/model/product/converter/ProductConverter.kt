package ru.itmo.soa.lab.storage.model.product.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.storage.model.organization.converter.OrganizationConverter
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class ProductConverter(
    private val coordinatesConverter: CoordinatesConverter,
    private val organizationConverter: OrganizationConverter,
) : DtoConverter<Product, ProductDto> {
    override fun toDto(entity: Product) = ProductDto(
        entity.id!!,
        entity.creationDate!!,
        entity.name,
        coordinatesConverter.toDto(entity.coordinates),
        entity.price,
        entity.partNumber,
        entity.manufactureCost,
        entity.unitOfMeasure,
        entity.manufacturer?.let { organizationConverter.toDto(it) },
    )
}