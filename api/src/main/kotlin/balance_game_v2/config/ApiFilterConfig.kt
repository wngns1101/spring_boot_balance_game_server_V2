package balance_game_v2.config

import balance_game_v2.api.v1.filter.ApiFilter
import balance_game_v2.api.v1.user.application.TokenManager
import balance_game_v2.api.v1.user.http.common.CookieUtils
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class ApiFilterConfig (){
    private val includeTokenFilterPaths = arrayOf(
        "$USER_V2_PREFIX/*",
        "$BOARD_V2_PREFIX/*",
        "$NOTIFICATION_V2_PREFIX/*",
    )
    @Bean
    fun tokenFilter(cookieUtils: CookieUtils, tokenManager: TokenManager): FilterRegistrationBean<ApiFilter> {
        val registrationBean = FilterRegistrationBean(ApiFilter(cookieUtils, tokenManager))
        registrationBean.order = Integer.MIN_VALUE
        registrationBean.addUrlPatterns(*includeTokenFilterPaths)
        registrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return registrationBean
    }
}
