package ru.itmo.soa.lab.shop.utils

import io.ktor.http.*

fun toParams(page: Int?, size: Int?, sort: Iterable<String>?) = Parameters.build {
    page?.let { append("page", it.toString()) }
    size?.let { append("size", it.toString()) }
    sort?.let { appendAll("sort", it) }
}
