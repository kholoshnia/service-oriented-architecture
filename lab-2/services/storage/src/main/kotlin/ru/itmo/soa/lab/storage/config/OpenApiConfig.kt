package ru.itmo.soa.lab.storage.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(@Value("\${springdoc.version}") appVersion: String): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Online shop storage API")
                .version(appVersion)
                .description("Online shop storage API provides access to the products.")
        )
}
