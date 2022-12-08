package ru.itmo.soa.lab.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class ShopApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<ShopApplication>(*args)
}
