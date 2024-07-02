package balance_game_v2.config

import balance_game_v2.api.v1.filter.ApiFilter
import balance_game_v2.api.v1.filter.HostFilter
import balance_game_v2.api.v1.user.application.TokenManager
import balance_game_v2.api.v1.user.http.common.CookieUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class ApiFilterConfig(
    @Value("\${dns}")
    private var dns: String
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
        val registrationBean = FilterRegistrationBean(HostFilter(dns))
        registrationBean.addUrlPatterns("/*")
        registrationBean.order = Ordered.LOWEST_PRECEDENCE
        return registrationBean
    }
}
