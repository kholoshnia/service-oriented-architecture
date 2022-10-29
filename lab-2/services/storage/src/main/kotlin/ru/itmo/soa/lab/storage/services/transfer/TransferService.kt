package ru.itmo.soa.lab.storage.services.transfer

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.itmo.soa.lab.shared.dto.product.CoordinatesDto
import ru.itmo.soa.lab.shared.dto.transfer.NewTransferDto
import ru.itmo.soa.lab.shared.dto.transfer.TransferDto
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.product.converter.CoordinatesConverter
import ru.itmo.soa.lab.storage.model.transfer.converter.NewTransferConverter
import ru.itmo.soa.lab.storage.model.transfer.converter.TransferConverter
import ru.itmo.soa.lab.storage.model.transfer.entity.TransferId
import ru.itmo.soa.lab.storage.model.transfer.repository.TransferRepository
import ru.itmo.soa.lab.storage.services.organization.OrganizationService
import ru.itmo.soa.lab.storage.services.product.ProductService
import ru.itmo.soa.lab.storage.utils.PageConverter
import java.time.LocalDateTime

@Service
class TransferService(
    private val organizationService: OrganizationService,
    private val productService: ProductService,
    private val transferRepository: TransferRepository,
    private val newTransferConverter: NewTransferConverter,
    private val transferConverter: TransferConverter,
    private val coordinatesConverter: CoordinatesConverter,
    private val pageConverter: PageConverter
) {
    fun getTransfersPage(pageable: Pageable) =
        pageConverter.toDto(transferRepository.findAll(pageable).map(transferConverter::toDto))

    @Transactional
    fun addTransfer(newTransferDto: NewTransferDto): TransferDto {
        if (newTransferDto.products.isEmpty()) throw TransferWithoutProductsException()
        val transfer = newTransferConverter.toEntity(newTransferDto)
        if (!transfer.products.all { it.manufacturer?.id == transfer.sender.id }) {
            throw TransferNotOwnerException()
        }
        if (!transfer.products.all { it.transfers.all { t -> t.finishedAt != null } }) {
            throw AlreadyInTransferException()
        }
        return transferConverter.toDto(transferRepository.save(transfer))
    }

    @Transactional
    fun updateCoordinates(transferId: TransferId, coordinatesDto: CoordinatesDto): TransferDto {
        val transfer = transferRepository.findById(transferId).orElseThrow { TransferNotFoundException() }
        transfer.coordinates = coordinatesConverter.toEntity(coordinatesDto)
        return transferConverter.toDto(transferRepository.save(transfer))
    }

    @Transactional
    fun finishTransfer(transferId: TransferId, receiverId: OrganizationId): TransferDto {
        val transfer = transferRepository.findById(transferId).orElseThrow { TransferNotFoundException() }
        val receiver = organizationService.getOrganizationById(receiverId)

        transfer.finishedAt = LocalDateTime.now()
        transfer.receiver = receiver
        transfer.coordinates = receiver.coordinates
        transfer.products.map {
            it.manufacturer = receiver
        }

        productService.saveAll(transfer.products)
        return transferConverter.toDto(transferRepository.save(transfer))
    }
}
