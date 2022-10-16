package ru.itmo.soa.lab.shared.dto.utils

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("page")
open class PageDto<T>(
    val page: Int,
    val size: Int,
    val total: Long,
    val data: List<T>
)
