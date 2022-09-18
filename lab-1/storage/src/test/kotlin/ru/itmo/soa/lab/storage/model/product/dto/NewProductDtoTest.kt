package ru.itmo.soa.lab.storage.model.product.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.model.product.entity.UnitOfMeasure
import java.time.LocalDate
import javax.validation.Validation


@SpringBootTest
class NewProductDtoTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(DtoUtil.createProductDto())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(
            validator.validate(
                ProductDto(
                    1,
                    LocalDate.now(),
                    "test",
                    DtoUtil.createCoordinatesDto(),
                    1,
                    "2test2",
                    2.2f,
                    UnitOfMeasure.KILOGRAMS,
                    DtoUtil.createOrganizationDto()
                )
            )
        ).isNotEmpty
    }
}
