package ru.itmo.soa.lab.storage.model.organization.converter

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class NewOrganizationConverterTest {
    @Autowired
    private lateinit var newOrganizationConverter: NewOrganizationConverter

    @Test
    fun `should return same organization given new organization dto`() {
        val newOrganizationDto = DtoUtil.createNewOrganizationDto()
        val organization = newOrganizationConverter.toEntity(newOrganizationDto)

        assertThat(organization.id).isNull()
        assertThat(organization.name).isEqualTo(newOrganizationDto.name)
        assertThat(organization.fullName).isEqualTo(newOrganizationDto.fullName)
        assertThat(organization.annualTurnover).isEqualTo(newOrganizationDto.annualTurnover)
        assertThat(organization.employeesCount).isEqualTo(newOrganizationDto.employeesCount)
    }

    @Test
    fun `should throw when is not implemented`() {
        val organization = EntityUtil.createOrganization()

        assertThatThrownBy {
            newOrganizationConverter.toDto(organization)
        }.isInstanceOf(NotImplementedError::class.java)
    }
}