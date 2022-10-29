package ru.itmo.soa.lab.storage.controller

import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.itmo.soa.lab.shared.dto.product.CoordinatesDto
import ru.itmo.soa.lab.shared.dto.transfer.NewTransferDto
import ru.itmo.soa.lab.storage.model.organization.entity.OrganizationId
import ru.itmo.soa.lab.storage.model.transfer.entity.TransferId
import ru.itmo.soa.lab.storage.services.transfer.TransferService

@RestController
@RequestMapping("/api/v1/transfers", produces = [MediaType.APPLICATION_XML_VALUE])
class TransferController(
    private val transferService: TransferService
) {
    @GetMapping
    fun getTransfersPage(@ParameterObject pageable: Pageable) =
        transferService.getTransfersPage(pageable)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addTransfer(@RequestBody newTransferDto: NewTransferDto) =
        transferService.addTransfer(newTransferDto)

    @PutMapping("/{transferId}/coordinates")
    fun updateCoordinates(
        @PathVariable("transferId") transferId: TransferId,
        @RequestBody coordinatesDto: CoordinatesDto
    ) = transferService.updateCoordinates(transferId, coordinatesDto)

    @PutMapping("/{transferId}/finish/{receiverId}")
    fun finishTransfer(
        @PathVariable("transferId") transferId: TransferId,
        @PathVariable("receiverId") receiverId: OrganizationId
    ) = transferService.finishTransfer(transferId, receiverId)
}
