package balance_game_v2.api.v1.filter

import balance_game_v2.api.support.error.ErrorCodes
import balance_game_v2.api.support.error.ErrorModel
import balance_game_v2.api.v1.user.application.TokenManager
import balance_game_v2.api.v1.user.http.common.CookieUtils
import balance_game_v2.config.USER_V2_PREFIX
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType

class ApiFilter(
    private val cookieUtils: CookieUtils,
    private val tokenManager: TokenManager,
) : Filter {
    val logger = LoggerFactory.getLogger(this::class.java)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse

        var token = req.getHeader("Authorization")

        if (req.requestURI == "$USER_V2_PREFIX/sign-up" ||
            req.requestURI == "$USER_V2_PREFIX/sign-in" ||
            req.requestURI == "$USER_V2_PREFIX/users/me/re-issue"
        ) {
            chain.doFilter(req, res)
            return
        }

        token = try {
            token.split(" ")[1]
        } catch (e: Exception) {
            sendError(res, ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
            return
        }

        try {
            if (tokenManager.validationAccessToken(token)) {
                if (tokenManager.isTokenExpired(token)) {
                    sendError(res, ErrorCodes.EXPIRED_TOKEN_ERROR)
                    return
                }
                val email = tokenManager.getUserEmailFromToken(token)
                req.setAttribute("email", email)

                chain.doFilter(req, res)
            } else {
                sendError(res, ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
            }
        } catch (e: Exception) {
            sendError(res, ErrorCodes.UNKNOWN_ERROR)
            return
        }
    }
}

private fun sendError(res: HttpServletResponse, code: ErrorCodes) {
    val error = ErrorModel(code = code.name, message = code.message)
    res.status = code.httpStatus.value()
    res.contentType = MediaType.APPLICATION_JSON_VALUE
    res.characterEncoding = "UTF-8"

    res.setHeader("Access-Control-Allow-Origin", "*")
    res.setHeader("Access-Control-Allow-Methods", "*")
    res.setHeader("Access-Control-Allow-Headers", "*")

    val jsonError = jacksonObjectMapper().writeValueAsString(error)
    res.writer.write(jsonError)
}
