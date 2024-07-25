package balance_game_v2.api

import balance_game_v2.api.client.SlackInternalErrorSender
import balance_game_v2.api.support.error.ErrorCodes
import balance_game_v2.api.support.error.ErrorModel
import balance_game_v2.api.v1.user.application.TokenManager
import balance_game_v2.api.v1.user.http.exception.ExpiredTokenException
import balance_game_v2.api.v1.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v1.user.http.exception.NotEqualsTokenException
import balance_game_v2.api.v1.user.http.exception.NotExistTokenException
import domain.auth.exception.NotSignUpUserException
import domain.auth.exception.PasswordMismatchException
import domain.error.AlreadyExistEmailException
import domain.error.AlreadySignUpException
import domain.error.BusinessException
import domain.user.UserService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.ContentCachingRequestWrapper

@RestControllerAdvice
class RestControllerAdvise(
    private val tokenManager: TokenManager,
    private val userService: UserService,
    private val slackInternalErrorSender: SlackInternalErrorSender
) {
    val log: Logger = LoggerFactory.getLogger(RestControllerAdvice::class.java)
    @ExceptionHandler
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ErrorModel> {
        return when (ex) {
            is AlreadyExistEmailException -> createResponse(ErrorCodes.ALREADY_EXIST_EMAIL_ERROR)
            is AlreadySignUpException -> createResponse(ErrorCodes.ALREADY_SIGN_UP_ERROR)
            is NotSignUpUserException -> createResponse(ErrorCodes.NOT_SIGN_UP_USER_ERROR)
            is PasswordMismatchException -> createResponse(ErrorCodes.PASSWORD_MISMATCH_ERROR)
            is ExpiredTokenException -> createResponse(ErrorCodes.EXPIRED_TOKEN_ERROR)
            is InvalidTokenTypeException -> createResponse(ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
            is NotEqualsTokenException -> createResponse(ErrorCodes.NOT_EQUALS_TOKEN_ERROR)
            is NotExistTokenException -> createResponse(ErrorCodes.NOT_EXIST_TOKEN_ERROR)
            else -> createResponse(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalServerException(ex: Exception, request: HttpServletRequest) {
        log.info(ex.message)
        val cachingRequest = ContentCachingRequestWrapper(request)
        try {
            val accessToken = request.getHeader("Authorization").split(" ")[1]
            val userEmail = tokenManager.getUserEmailFromToken(accessToken)
            val userId = userService.getUserByEmail(userEmail).userId

//            slackInternalErrorSender.execute(cachingRequest, ex, userId)
        } catch (e: Exception) {
//            slackInternalErrorSender.execute(cachingRequest, ex, null)
        }

        createResponse(ErrorCodes.UNKNOWN_ERROR)
    }
}
fun createResponse(error: ErrorCodes): ResponseEntity<ErrorModel> {
    val errorModel = ErrorModel(
        code = error.name,
        message = error.message
    )
    return ResponseEntity(errorModel, error.httpStatus)
}
