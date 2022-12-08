package ru.itmo.soa.lab.storage.model.product.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.shared.dto.product.ProductDto
import ru.itmo.soa.lab.shared.dto.product.UnitOfMeasure
import ru.itmo.soa.lab.storage.DtoUtil
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
