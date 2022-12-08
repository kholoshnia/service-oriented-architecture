package ru.itmo.soa.lab.storage.utils

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import ru.itmo.soa.lab.shared.dto.utils.PageDto

@Component
class PageConverter {
    fun <T> toDto(entity: Page<T>) = PageDto<T>(
        entity.number + 1,
        entity.size,
        entity.totalElements,
        entity.content
    )
}