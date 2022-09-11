package ru.itmo.soa.lab.storage.utils

class PageDto<T>(
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val data: List<T>,
)