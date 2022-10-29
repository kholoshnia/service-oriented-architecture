package ru.itmo.soa.lab.shared.dto.transfer

import com.fasterxml.jackson.annotation.JsonRootName
import ru.itmo.soa.lab.shared.dto.organization.OrganizationDto
import ru.itmo.soa.lab.shared.dto.product.CoordinatesDto
import ru.itmo.soa.lab.shared.dto.product.ProductDto
import java.time.LocalDateTime

@JsonRootName("transfer")
data class TransferDto(
    val id: Int? = null,
    var coordinates: CoordinatesDto,
    var products: List<ProductDto>,
    var sender: OrganizationDto,
    var receiver: OrganizationDto? = null,
    val startedAt: LocalDateTime? = null,
    var finishedAt: LocalDateTime? = null,
)