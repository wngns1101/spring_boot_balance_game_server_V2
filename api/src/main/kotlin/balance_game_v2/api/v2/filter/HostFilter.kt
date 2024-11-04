package balance_game_v2.api.v2.filter

import balance_game_v2.api.client.FeignClient
import balance_game_v2.api.client.dto.WhoIsResponseDTO
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

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse

        val requestIp = req.remoteAddr

        println("요청 url: $requestIp")

        val whoIsResponse = feignClient.getWhois(accessCode, requestIp, "json")
        val apiResponse: WhoIsResponseDTO = objectMapper.readValue(whoIsResponse)

        if (requestIp == "127.0.0.1" || apiResponse.response.whois.countryCode == "KR" || apiResponse.response.whois.countryCode == "US") {
            chain.doFilter(request, response)
        } else {
            res.status = HttpServletResponse.SC_FORBIDDEN // 403 Forbidden 상태 설정
            res.writer.write("Access denied: 접근할 수 없습니다.")
            res.writer.flush() // 응답 본문 전송
        }
    }
}
