package ru.itmo.soa.lab.storage

import org.springframework.data.domain.PageImpl
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.storage.model.organization.entity.Organization
import ru.itmo.soa.lab.storage.model.organization.entity.Coordinates
import ru.itmo.soa.lab.storage.model.product.entity.Product
import java.time.LocalDate

object EntityUtil {
    fun createOrganization(name: String = "test", setId: Boolean = true): Organization {
        val organization = Organization("test", "test test$name", 1, 2)
        if (setId) organization.id = 1
        return organization
    }

    fun createCoordinates() = Coordinates(1, 2)

    fun createProduct(name: String = "test", setId: Boolean = true, price: Int = 1): Product {
        val product = Product(
            name,
            createCoordinates(),
            price,
            "test_test_test_test_test_test$name",
            2.2f,
            UnitOfMeasure.KILOGRAMS,
            createOrganization(name, setId)
        )
        if (setId) product.id = 1
        product.creationDate = LocalDate.now()
        return product
    }

    fun createPage() = PageImpl(listOf("test"))
}