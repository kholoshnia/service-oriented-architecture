package ru.itmo.soa.lab.storage.model.product.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.product.NewProductDto
import ru.itmo.soa.lab.storage.model.organization.converter.NewOrganizationConverter
import ru.itmo.soa.lab.storage.model.product.entity.Product
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class NewProductConverter(
    private val newOrganizationConverter: NewOrganizationConverter,
) : DtoConverter<Product, NewProductDto> {
    override fun toEntity(dto: NewProductDto) = Product(
        dto.name,
        dto.price,
        dto.partNumber,
        dto.manufactureCost,
        dto.unitOfMeasure,
        dto.manufacturer?.let(newOrganizationConverter::toEntity),
    )
}