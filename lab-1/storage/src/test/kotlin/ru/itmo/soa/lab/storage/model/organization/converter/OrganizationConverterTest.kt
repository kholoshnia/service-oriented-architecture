package ru.itmo.soa.lab.storage.model.organization.converter

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class OrganizationConverterTest {
    @Autowired
    private lateinit var organizationConverter: OrganizationConverter

    @Test
    fun `should return same organization given organization dto`() {
        val organization = EntityUtil.createOrganization()
        val organizationDto = organizationConverter.toDto(organization)

        assertThat(organizationDto.id).isEqualTo(organization.id)
        assertThat(organizationDto.name).isEqualTo(organization.name)
        assertThat(organizationDto.fullName).isEqualTo(organization.fullName)
        assertThat(organizationDto.annualTurnover).isEqualTo(organization.annualTurnover)
        assertThat(organizationDto.employeesCount).isEqualTo(organization.employeesCount)
    }

    @Test
    fun `should throw when is not implemented`() {
        val organizationDto = DtoUtil.createOrganizationDto()

        assertThatThrownBy {
            organizationConverter.toEntity(organizationDto)
        }.isInstanceOf(NotImplementedError::class.java)
    }
}