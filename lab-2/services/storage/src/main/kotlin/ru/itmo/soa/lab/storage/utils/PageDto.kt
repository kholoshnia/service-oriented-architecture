package ru.itmo.soa.lab.storage.utils

import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("page")
open class PageDto<T>(
    val page: Int,
    val size: Int,
    val total: Int,
    val data: List<T>
)
