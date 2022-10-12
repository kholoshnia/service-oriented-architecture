package ru.itmo.soa.lab.storage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class StorageApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    runApplication<StorageApplication>(*args)
}
