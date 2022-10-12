package ru.itmo.soa.lab.storage.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

class LoggerDelegate<in R : Any> : ReadOnlyProperty<R, Logger> {
    private fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

    private fun <T : Any> getClassForLogging(javaClass: Class<T>) = javaClass.enclosingClass
        ?.takeIf { it.kotlin.companionObject?.java == javaClass } ?: javaClass

    override fun getValue(thisRef: R, property: KProperty<*>) =
        getLogger(getClassForLogging(thisRef.javaClass))
}
