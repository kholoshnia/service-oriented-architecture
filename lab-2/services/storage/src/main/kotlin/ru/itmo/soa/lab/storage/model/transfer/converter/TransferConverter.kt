package ru.itmo.soa.lab.storage.model.transfer.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.transfer.TransferDto
import ru.itmo.soa.lab.storage.model.organization.converter.OrganizationConverter
import ru.itmo.soa.lab.storage.model.product.converter.CoordinatesConverter
import ru.itmo.soa.lab.storage.model.product.converter.ProductConverter
import ru.itmo.soa.lab.storage.model.transfer.entity.Transfer
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class TransferConverter(
    private val coordinatesConverter: CoordinatesConverter,
    private val productConverter: ProductConverter,
    private val organizationConverter: OrganizationConverter
) : DtoConverter<Transfer, TransferDto> {
    override fun toDto(entity: Transfer) = TransferDto(
        entity.id,
        coordinatesConverter.toDto(entity.coordinates),
        entity.products.map(productConverter::toDto),
        organizationConverter.toDto(entity.sender),
        entity.receiver?.let(organizationConverter::toDto),
        entity.startedAt,
        entity.finishedAt,
    )
}