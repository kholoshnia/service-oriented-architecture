package ru.itmo.soa.lab.storage.model.product.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.EntityUtil
import javax.validation.Validation


@SpringBootTest
class ProductTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(EntityUtil.createProduct())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(
            validator.validate(
                Product(
                    "test",
                    EntityUtil.createCoordinates(),
                    -1,
                    "2test2",
                    -2.2f,
                    UnitOfMeasure.KILOGRAMS,
                    EntityUtil.createOrganization()
                )
            )
        ).isNotEmpty
    }
}
