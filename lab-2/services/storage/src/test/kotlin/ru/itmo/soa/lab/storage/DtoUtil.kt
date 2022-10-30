package ru.itmo.soa.lab.storage

import ru.itmo.soa.lab.shared.dto.organization.NewOrganizationDto
import ru.itmo.soa.lab.shared.dto.organization.OrganizationDto
import ru.itmo.soa.lab.shared.dto.organization.CoordinatesDto
import ru.itmo.soa.lab.shared.dto.product.NewProductDto
import ru.itmo.soa.lab.shared.dto.product.ProductDto
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
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