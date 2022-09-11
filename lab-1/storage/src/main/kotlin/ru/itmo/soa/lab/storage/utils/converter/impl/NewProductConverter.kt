package ru.itmo.soa.lab.storage.utils.converter.impl

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.storage.model.product.dto.NewProductDto
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.utils.converter.DtoConverter

@Component
class NewProductConverter(
    private val coordinatesConverter: CoordinatesConverter
) : DtoConverter<Product, NewProductDto> {
    override fun toEntity(dto: NewProductDto) = Product(
        dto.name,
        coordinatesConverter.toEntity(dto.coordinates),
        dto.price,
        dto.partNumber,
        dto.manufactureCost,
        dto.unitOfMeasure,
        dto.manufacturer,
    )
}