package balance_game_v2.client

import balance_game_v2.api.client.FeignClient
import balance_game_v2.api.v2.filter.ApiFilter
import balance_game_v2.api.v2.filter.HostFilter
import balance_game_v2.api.v2.user.application.TokenManager
import balance_game_v2.api.v2.user.http.common.CookieUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class ApiFilterConfig(
    @Value("\${kisa.access-token}")
    private val accessToken: String,
    private val feignClient: FeignClient,
) {
    private val includeTokenFilterPaths = arrayOf(
        "$USER_V2_PREFIX/*",
        "$BOARD_V2_PREFIX/*",
        "$NOTIFICATION_V2_PREFIX/*",
    )
    @Bean
    fun tokenFilter(cookieUtils: CookieUtils, tokenManager: TokenManager): FilterRegistrationBean<ApiFilter> {
        val registrationBean = FilterRegistrationBean(ApiFilter(cookieUtils, tokenManager))
        registrationBean.addUrlPatterns(*includeTokenFilterPaths)
        registrationBean.order = Ordered.LOWEST_PRECEDENCE + 10
        return registrationBean
    }

    @Bean
    fun hostFilter(): FilterRegistrationBean<HostFilter> {
        val registrationBean = FilterRegistrationBean(HostFilter(accessToken, feignClient))
        registrationBean.addUrlPatterns("/*")
        registrationBean.order = Ordered.LOWEST_PRECEDENCE
        return registrationBean
    }
}
