package ru.itmo.soa.lab.storage.model.organization.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.shared.dto.organization.OrganizationDto
import ru.itmo.soa.lab.storage.DtoUtil
import javax.validation.Validation


@SpringBootTest
class OrganizationDtoTest {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should return empty errors given valid object`() {
        assertThat(validator.validate(DtoUtil.createOrganizationDto())).isEmpty()
    }

    @Test
    fun `should return not empty errors given invalid object`() {
        assertThat(
            validator.validate(
                OrganizationDto(
                    -1,
                    "   ",
                    "test",
                    -1,
                    -1,
                    DtoUtil.createCoordinatesDto()
                )
            )
        ).isNotEmpty
    }
}
