package ru.itmo.soa.lab.storage.model.product.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.shared.dto.organization.CoordinatesDto
import ru.itmo.soa.lab.storage.DtoUtil
import javax.validation.Validation


@SpringBootTest
class CoordinatesDtoTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(DtoUtil.createCoordinatesDto())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(validator.validate(CoordinatesDto(500, -200))).isNotEmpty
    }
}
