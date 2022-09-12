package ru.itmo.soa.lab.storage.utils

data class PageDto<T>(
    val page: Int,
    val size: Int,
    val total: Int,
    val data: List<T>
)
