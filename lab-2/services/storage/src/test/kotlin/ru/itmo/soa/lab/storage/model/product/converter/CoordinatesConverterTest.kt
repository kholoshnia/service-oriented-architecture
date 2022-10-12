package ru.itmo.soa.lab.storage.model.product.converter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class CoordinatesConverterTest {
    @Autowired
    private lateinit var coordinatesConverter: CoordinatesConverter

    @Test
    fun `should return same coordinates given coordinates dto`() {
        val coordinatesDto = DtoUtil.createCoordinatesDto()
        val coordinates = coordinatesConverter.toEntity(coordinatesDto)

        assertThat(coordinates.x).isEqualTo(coordinatesDto.x)
        assertThat(coordinates.y).isEqualTo(coordinatesDto.y)
    }

    @Test
    fun `should return same coordinates dto given coordinates`() {
        val coordinates = EntityUtil.createCoordinates()
        val coordinatesDto = coordinatesConverter.toDto(coordinates)

        assertThat(coordinatesDto.x).isEqualTo(coordinates.x)
        assertThat(coordinatesDto.y).isEqualTo(coordinates.y)
    }
}
