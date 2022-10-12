package ru.itmo.soa.lab.storage.model.product.converter

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class NewProductConverterTest {
    @Autowired
    private lateinit var newProductConverter: NewProductConverter

    @Test
    fun `should return same product given new product dto`() {
        val newProductDto = DtoUtil.createNewProductDto()
        val product = newProductConverter.toEntity(newProductDto)

        assertThat(product.id).isNull()
        assertThat(product.creationDate).isNull()
        assertThat(product.name).isEqualTo(newProductDto.name)
        assertThat(product.coordinates.x).isEqualTo(newProductDto.coordinates.x)
        assertThat(product.coordinates.y).isEqualTo(newProductDto.coordinates.y)
        assertThat(product.price).isEqualTo(newProductDto.price)
        assertThat(product.partNumber).isEqualTo(newProductDto.partNumber)
        assertThat(product.manufactureCost).isEqualTo(newProductDto.manufactureCost)
        assertThat(product.unitOfMeasure).isEqualTo(newProductDto.unitOfMeasure)
        assertThat(product.manufacturer?.id).isNull()
        assertThat(product.manufacturer?.name).isEqualTo(newProductDto.manufacturer?.name)
        assertThat(product.manufacturer?.fullName).isEqualTo(newProductDto.manufacturer?.fullName)
        assertThat(product.manufacturer?.annualTurnover).isEqualTo(newProductDto.manufacturer?.annualTurnover)
        assertThat(product.manufacturer?.employeesCount).isEqualTo(newProductDto.manufacturer?.employeesCount)
    }

    @Test
    fun `should throw when is not implemented`() {
        val product = EntityUtil.createProduct()

        Assertions.assertThatThrownBy {
            newProductConverter.toDto(product)
        }.isInstanceOf(NotImplementedError::class.java)
    }
}
