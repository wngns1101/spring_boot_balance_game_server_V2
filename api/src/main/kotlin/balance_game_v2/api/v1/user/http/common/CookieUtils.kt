package balance_game_v2.api.v1.user.http.common

import jakarta.servlet.http.Cookie
import org.springframework.stereotype.Component

@Component
class CookieUtils() {
    fun createCookie(key: String, value: String, httpOnly: Boolean, path: String): Cookie {
        val cookie = Cookie(key, value)
        cookie.isHttpOnly = httpOnly
        cookie.maxAge = 86400
        cookie.path = path

        return cookie
    }

    fun getCookieValue(cookies: Array<Cookie>?, name: String): String? {
        return cookies?.firstOrNull { it.name == name }?.value
    }
}
