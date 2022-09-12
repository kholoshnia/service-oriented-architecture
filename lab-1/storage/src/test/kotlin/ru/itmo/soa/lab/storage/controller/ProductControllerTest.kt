package ru.itmo.soa.lab.storage.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should add product`() {

    }

    @Test
    fun `should get product by id`() {

    }

    @Test
    fun `should update product by id`() {

    }

    @Test
    fun `should delete product`() {

    }

    @Test
    fun `should get products page`() {

    }

    @Test
    fun `should get products page with id filter`() {

    }

    @Test
    fun `should get products page with name filter`() {

    }

    @Test
    fun `should get max price product cost groups`() {

    }

    @Test
    fun `should get manufacture cost groups`() {

    }

    @Test
    fun `should get by part number greater than given part number`() {

    }
}