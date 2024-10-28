package balance_game_v2.client

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val USER_V2 = "유저 API"
const val BOARD_V2 = "게임 API"
const val NOTIFICATION_V2 = "알림 API"
const val USER_V2_PREFIX = "/user/v2"
const val BOARD_V2_PREFIX = "/board/v2"
const val NOTIFICATION_V2_PREFIX = "/notification/v2"
const val COMMON_V2 = "공통 API"
const val COMMON_V2_PREFIX = "/common/v2"

@OpenAPIDefinition(
    info = Info(title = "balanceGame Api 명세서", version = "v2"),
)
@Configuration
class SwaggerConfig {
    @Bean
    fun v2Api(): GroupedOpenApi {
        val paths: Array<String> = arrayOf(
            "$USER_V2_PREFIX/**",
            "$BOARD_V2_PREFIX/**",
            "$NOTIFICATION_V2_PREFIX/**",
            "$COMMON_V2_PREFIX/**",
        )

        return GroupedOpenApi.builder()
            .group("v2")
            .pathsToMatch(*paths)
            .addOpenApiCustomizer(
                buildSecurityOpenApi(true)
            )
            .build()
    }

    fun buildSecurityOpenApi(active: Boolean): OpenApiCustomizer {
        val securityScheme = SecurityScheme()

        securityScheme.name = "Authorization"
        securityScheme.type = SecurityScheme.Type.HTTP
        securityScheme.`in` = SecurityScheme.In.HEADER
        securityScheme.bearerFormat = "JWT"
        securityScheme.scheme = "bearer"

        return OpenApiCustomizer {
            it.addSecurityItem(SecurityRequirement().addList("jwt token"))
            it.components.addSecuritySchemes("jwt token", securityScheme)
        }
    }
}
