package balance_game_v2.api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "test", url = "http://apis.data.go.kr/B551505")
interface FeignClient {
    @GetMapping("/whois/ipas_country_code")
    fun getWhois(@RequestParam("serviceKey") key: String, @RequestParam("query") query: String, @RequestParam("answer") answer: String): String
}
