package ru.itmo.soa.lab.storage.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.itmo.soa.lab.storage.EntityUtil

@SpringBootTest
class PageConverterTest {
    @Autowired
    private lateinit var pageConverter: PageConverter

    @Test
    fun `should convert to page dto given page`() {
        val page = EntityUtil.createPage()
        val pageDto = pageConverter.toDto(page)

        assertThat(pageDto.page).isEqualTo(page.number + 1)
        assertThat(pageDto.size).isEqualTo(page.size)
        assertThat(pageDto.total).isEqualTo(page.totalElements)
        assertThat(pageDto.data).containsAll(page.content)
    }
}