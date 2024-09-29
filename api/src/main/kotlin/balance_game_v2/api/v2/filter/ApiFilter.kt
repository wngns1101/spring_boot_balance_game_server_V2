package balance_game_v2.api.v2.filter

import balance_game_v2.api.support.error.ErrorCodes
import balance_game_v2.api.support.error.ErrorModel
import balance_game_v2.api.v2.user.application.TokenManager
import balance_game_v2.api.v2.user.http.common.CookieUtils
import balance_game_v2.config.BOARD_V2_PREFIX
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
            req.requestURI == "$USER_V2_PREFIX/users/me/re-issue" ||
            req.requestURI == "$USER_V2_PREFIX/check-account-name" ||
            req.requestURI == "$USER_V2_PREFIX/profile" ||
            req.requestURI == "$USER_V2_PREFIX/email-certificate" ||
            req.requestURI == "$USER_V2_PREFIX/check-email-certificate" ||
            req.requestURI == "$USER_V2_PREFIX/find-account-name" ||
            req.requestURI == "$USER_V2_PREFIX/temporary-password" ||
            req.requestURI.startsWith("$BOARD_V2_PREFIX/public/")
        ) {
            chain.doFilter(req, res)
            return
        }

        if (req.requestURI == "$BOARD_V2_PREFIX/boards" ||
            req.requestURI == "$BOARD_V2_PREFIX/boards/today-recommend-game" ||
            req.requestURI == "$BOARD_V2_PREFIX/recommend-review" ||
            req.requestURI.endsWith("/related-boards") ||
            req.requestURI.endsWith("/reviews")
        ) {
            if (token != null) {
                val splitToken = try {
                    token.split(" ")[1]
                } catch (e: Exception) {
                    sendError(res, ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
                    return
                }
                val email = tokenManager.getUserEmailFromToken(splitToken)
                req.setAttribute("accountName", email)

                chain.doFilter(req, res)
                return
            } else {
                req.setAttribute("accountName", null)

                chain.doFilter(req, res)
                return
            }
        }

        val splitToken = try {
            token.split(" ")[1]
        } catch (e: Exception) {
            sendError(res, ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
            return
        }

        try {
            if (tokenManager.validationAccessToken(splitToken)) {
                if (tokenManager.isTokenExpired(splitToken)) {
                    sendError(res, ErrorCodes.EXPIRED_TOKEN_ERROR)
                    return
                }
                val email = tokenManager.getUserEmailFromToken(splitToken)
                req.setAttribute("accountName", email)

                chain.doFilter(req, res)
                return
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
