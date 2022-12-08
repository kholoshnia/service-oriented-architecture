package ru.itmo.soa.lab.storage.model.organization.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.EntityUtil
import javax.validation.Validation


@SpringBootTest
class OrganizationTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(EntityUtil.createOrganization())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(validator.validate(Organization("   ", "test", -1, -1, EntityUtil.createCoordinates()))).isNotEmpty
    }
}
