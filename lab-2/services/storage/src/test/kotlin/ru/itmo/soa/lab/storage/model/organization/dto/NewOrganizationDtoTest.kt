package ru.itmo.soa.lab.storage.model.organization.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import javax.validation.Validation


@SpringBootTest
class NewOrganizationDtoTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(DtoUtil.createNewOrganizationDto())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(validator.validate(NewOrganizationDto("   ", "test", -1, -1))).isNotEmpty
    }
}
