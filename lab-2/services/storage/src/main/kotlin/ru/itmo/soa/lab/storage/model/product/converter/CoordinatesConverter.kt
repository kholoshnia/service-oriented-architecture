package ru.itmo.soa.lab.storage.model.product.converter

import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.organization.CoordinatesDto
import ru.itmo.soa.lab.storage.model.product.entity.Coordinates
import ru.itmo.soa.lab.storage.utils.DtoConverter

@Component
class CoordinatesConverter : DtoConverter<Coordinates, CoordinatesDto> {
    override fun toEntity(dto: CoordinatesDto) = Coordinates(
        dto.x,
        dto.y
    )

    override fun toDto(entity: Coordinates) = CoordinatesDto(
        entity.x,
        entity.y
    )
}