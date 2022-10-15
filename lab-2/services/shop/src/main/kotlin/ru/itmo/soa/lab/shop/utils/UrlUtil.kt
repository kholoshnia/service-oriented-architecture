package ru.itmo.soa.lab.shop.utils

import io.ktor.http.URLBuilder

fun URLBuilder.addPageableParams(pageableParams: PageableParams) {
    parameters["page"] = pageableParams.page.toString()
    parameters["size"] = pageableParams.size.toString()
    pageableParams.sort?.forEachIndexed { index, s ->
        if (index == pageableParams.sort.size) return@forEachIndexed
        parameters["sort"] = "${s[index]},${s[index + 1]}"
    }
}
