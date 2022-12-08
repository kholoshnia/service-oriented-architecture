package ru.itmo.soa.lab.storage.model.transfer.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.transfer.NewTransferDto
import ru.itmo.soa.lab.storage.model.transfer.entity.Transfer
import ru.itmo.soa.lab.storage.services.organization.OrganizationService
import ru.itmo.soa.lab.storage.services.product.ProductService
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class NewTransferConverter(
    private val organizationService: OrganizationService,
    private val productService: ProductService
) : DtoConverter<Transfer, NewTransferDto> {
    override fun toEntity(dto: NewTransferDto): Transfer {
        val sender = organizationService.getOrganizationById(dto.senderId)
        return Transfer(
            sender.coordinates,
            productService.getAllById(dto.products),
            sender,
        )
    }
}