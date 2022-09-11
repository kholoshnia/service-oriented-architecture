package ru.itmo.soa.lab.storage.utils.converter.impl

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.utils.converter.DtoConverter

@Component
class ProductConverter(
    private val coordinatesConverter: CoordinatesConverter
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
        entity.manufacturer,
    )
}