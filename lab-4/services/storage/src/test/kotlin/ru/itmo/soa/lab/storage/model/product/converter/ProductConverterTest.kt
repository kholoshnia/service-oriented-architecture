package ru.itmo.soa.lab.storage.model.product.converter

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.DtoUtil
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class ProductConverterTest {
    @Autowired
    private lateinit var productConverter: ProductConverter

    @Test
    fun `should return same product dto given product`() {
        val product = EntityUtil.createProduct()
        val productDto = productConverter.toDto(product)

        assertThat(productDto.id).isEqualTo(product.id)
        assertThat(productDto.creationDate).isEqualTo(product.creationDate)
        assertThat(productDto.name).isEqualTo(product.name)
        assertThat(productDto.price).isEqualTo(product.price)
        assertThat(productDto.partNumber).isEqualTo(product.partNumber)
        assertThat(productDto.manufactureCost).isEqualTo(product.manufactureCost)
        assertThat(productDto.unitOfMeasure).isEqualTo(product.unitOfMeasure)
        assertThat(productDto.manufacturer?.id).isEqualTo(product.manufacturer?.id)
        assertThat(productDto.manufacturer?.name).isEqualTo(product.manufacturer?.name)
        assertThat(productDto.manufacturer?.fullName).isEqualTo(product.manufacturer?.fullName)
        assertThat(productDto.manufacturer?.annualTurnover).isEqualTo(product.manufacturer?.annualTurnover)
        assertThat(productDto.manufacturer?.employeesCount).isEqualTo(product.manufacturer?.employeesCount)
    }

    @Test
    fun `should throw when is not implemented`() {
        val productDto = DtoUtil.createProductDto()

        Assertions.assertThatThrownBy {
            productConverter.toEntity(productDto)
        }.isInstanceOf(NotImplementedError::class.java)
    }
}
