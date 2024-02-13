package org.example.balance_game_v2.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val USER_V2 = "유저 API"
const val USER_V2_PREFIX = "/user/v2"

@OpenAPIDefinition(
    info = Info(title = "balanceGame Api 명세서", version = "v2")
)

@Configuration
class SwaggerConfig {
    @Bean
    fun v2Api(): GroupedOpenApi {
        val paths: Array<String> = arrayOf("$USER_V2_PREFIX/**")
        return GroupedOpenApi.builder()
            .group("v2")
            .pathsToMatch(*paths)
            .build()
    }
}