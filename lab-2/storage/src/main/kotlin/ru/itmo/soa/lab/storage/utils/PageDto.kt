package ru.itmo.soa.lab.storage.utils

open class PageDto<T>(
    val page: Int,
    val size: Int,
    val total: Int,
    val data: List<T>
)
