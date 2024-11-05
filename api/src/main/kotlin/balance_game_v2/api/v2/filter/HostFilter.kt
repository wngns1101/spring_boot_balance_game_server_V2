package balance_game_v2.api.v2.filter

import balance_game_v2.api.client.FeignClient
import balance_game_v2.api.client.dto.WhoIsResponseDTO
import balance_game_v2.client.BOARD_V2_PREFIX
import balance_game_v2.client.COMMON_V2_PREFIX
import balance_game_v2.client.NOTIFICATION_V2_PREFIX
import balance_game_v2.client.USER_V2_PREFIX
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class HostFilter(
    private val accessCode: String,
    private val feignClient: FeignClient,
) : Filter {
    private val objectMapper = ObjectMapper().registerModules(KotlinModule())

    private val allowUrlPatterns = listOf(
        Regex("$USER_V2_PREFIX/.*"),
        Regex("$BOARD_V2_PREFIX/.*"),
        Regex("$COMMON_V2_PREFIX/.*"),
        Regex("$NOTIFICATION_V2_PREFIX/.*")
    )

    private val swaggerUrlPatterns = listOf(
        Regex("/swagger-ui/.*"),
        Regex("/swagger-ui.html"),
        Regex("/swagger-resources/.*"),
        Regex("/v3/api-docs"),
        Regex("/v3/api-docs/.*"),
        Regex("/configuration/ui"),
        Regex("/configuration/security"),
        Regex("/webjars/.*")
    )

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        val requestIp = req.remoteAddr
        val requestUri = req.requestURI

        if (swaggerUrlPatterns.any { it.matches(requestUri) }) {
            chain.doFilter(request, response)
            return
        }

        if (allowUrlPatterns.none { it.matches(requestUri) }) {
            res.status = HttpServletResponse.SC_FORBIDDEN
            res.writeAccessDenied()
            return
        }

        val whoIsResponse = feignClient.getWhois(accessCode, requestIp, "json")
        val apiResponse: WhoIsResponseDTO = objectMapper.readValue(whoIsResponse)

        if (requestIp == "127.0.0.1" || apiResponse.response.whois.countryCode in listOf("KR", "US")) {
            chain.doFilter(request, response)
        } else {
            res.status = HttpServletResponse.SC_FORBIDDEN
            res.writeAccessDenied()
        }
    }

    private fun HttpServletResponse.writeAccessDenied() {
        this.contentType = "text/html; charset=UTF-8"
        this.characterEncoding = "UTF-8"
        this.writer.write("Access denied: 접근할 수 없습니다.")
        this.writer.flush()
    }
}
