package ru.itmo.soa.lab.shop.utils

import javax.ws.rs.QueryParam

class PageableParams(
    @QueryParam("page")
    val page: Int = 1,

    @QueryParam("size")
    val size: Int = 10,

    @QueryParam("sort")
    val sort: List<String>? = null
)