package ru.itmo.soa.lab.storage.model.product.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.EntityUtil
import ru.itmo.soa.lab.storage.model.organization.entity.Coordinates
import javax.validation.Validation


@SpringBootTest
class CoordinatesTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(EntityUtil.createCoordinates())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(validator.validate(Coordinates(500, -200))).isNotEmpty
    }
}
