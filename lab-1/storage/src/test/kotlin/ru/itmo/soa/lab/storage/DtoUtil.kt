package ru.itmo.soa.lab.storage

import ru.itmo.soa.lab.storage.model.organization.dto.NewOrganizationDto
import ru.itmo.soa.lab.storage.model.organization.dto.OrganizationDto
import ru.itmo.soa.lab.storage.model.product.dto.CoordinatesDto
import ru.itmo.soa.lab.storage.model.product.dto.NewProductDto
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto
import ru.itmo.soa.lab.storage.model.product.entity.UnitOfMeasure
import java.time.LocalDate

object DtoUtil {
    fun createNewOrganizationDto() = NewOrganizationDto("test", "test test", 1, 2)

    fun createOrganizationDto() = OrganizationDto(1, "test", "test test", 1, 2)

    fun createCoordinatesDto() = CoordinatesDto(1, 2)

    fun createNewProductDto(name: String = "test", setManufacturer: Boolean = true) = NewProductDto(
        name,
        createCoordinatesDto(),
        1,
        "test_test_test_test_test_test",
        2.2f,
        UnitOfMeasure.KILOGRAMS,
        if (setManufacturer) createNewOrganizationDto() else null
    )

    fun createProductDto() = ProductDto(
        1,
        LocalDate.now(),
        "test",
        createCoordinatesDto(),
        1,
        "test_test_test_test_test_test",
        2.2f,
        UnitOfMeasure.KILOGRAMS,
        createOrganizationDto()
    )
}