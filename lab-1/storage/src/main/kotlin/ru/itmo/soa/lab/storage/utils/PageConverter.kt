package ru.itmo.soa.lab.storage.utils

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class PageConverter {
    fun <T> toDto(entity: Page<T>) = PageDto<T>(
        entity.number,
        entity.size,
        entity.totalPages,
        entity.content
    )
}