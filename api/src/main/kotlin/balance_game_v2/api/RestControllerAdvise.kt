package balance_game_v2.api

import balance_game_v2.api.client.SlackInternalErrorSender
import balance_game_v2.api.support.error.ErrorCodes
import balance_game_v2.api.support.error.ErrorModel
import balance_game_v2.api.v2.user.application.TokenManager
import balance_game_v2.api.v2.user.http.exception.ExpiredTokenException
import balance_game_v2.api.v2.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v2.user.http.exception.NotEqualsTokenException
import balance_game_v2.api.v2.user.http.exception.NotExistTokenException
import balance_game_v2.domain.auth.exception.BlockUserException
import balance_game_v2.domain.auth.exception.NotFoundUserException
import balance_game_v2.domain.auth.exception.NotSignUpUserException
import balance_game_v2.domain.auth.exception.PasswordMismatchException
import balance_game_v2.domain.auth.exception.WithDrawUserException
import balance_game_v2.domain.board.exception.AlreadyExistReviewException
import balance_game_v2.domain.board.exception.NotJoinedGameException
import balance_game_v2.domain.error.AlreadyExistAccountNameException
import balance_game_v2.domain.error.AlreadyExistEmailException
import balance_game_v2.domain.error.AlreadySignUpException
import balance_game_v2.domain.error.BusinessException
import balance_game_v2.domain.error.InvalidUserException
import balance_game_v2.domain.error.NotFoundAccountNameException
import balance_game_v2.domain.error.NotFoundBoardException
import balance_game_v2.domain.error.NotFoundEmailException
import balance_game_v2.domain.error.NotFoundReviewException
import balance_game_v2.domain.user.UserService
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
            is AlreadyExistAccountNameException -> createResponse(ErrorCodes.ALREADY_EXIST_ACCOUNT_NAME_ERROR)
            is AlreadySignUpException -> createResponse(ErrorCodes.ALREADY_SIGN_UP_ERROR)
            is NotSignUpUserException -> createResponse(ErrorCodes.NOT_SIGN_UP_USER_ERROR)
            is PasswordMismatchException -> createResponse(ErrorCodes.PASSWORD_MISMATCH_ERROR)
            is ExpiredTokenException -> createResponse(ErrorCodes.EXPIRED_TOKEN_ERROR)
            is InvalidTokenTypeException -> createResponse(ErrorCodes.INVALID_TOKEN_TYPE_ERROR)
            is NotEqualsTokenException -> createResponse(ErrorCodes.NOT_EQUALS_TOKEN_ERROR)
            is NotExistTokenException -> createResponse(ErrorCodes.NOT_EXIST_TOKEN_ERROR)
            is WithDrawUserException -> createResponse(ErrorCodes.WITH_DRAW_USER_ERROR)
            is BlockUserException -> createResponse(ErrorCodes.BLOCK_USER_ERROR)
            is NotJoinedGameException -> createResponse(ErrorCodes.NOT_SIGNED_GAME_ERROR)
            is AlreadyExistReviewException -> createResponse(ErrorCodes.ALREADY_EXIST_REVIEW_ERROR)
            is NotFoundEmailException -> createResponse(ErrorCodes.NOT_FOUND_EMAIL_ERROR)
            is NotFoundUserException -> createResponse(ErrorCodes.NOT_FOUND_USER_ERROR)
            is NotFoundBoardException -> createResponse(ErrorCodes.NOT_FOUND_BOARD_ERROR)
            is NotFoundReviewException -> createResponse(ErrorCodes.NOT_FOUND_REVIEW_ERROR)
            is NotFoundAccountNameException -> createResponse(ErrorCodes.NOT_FOUND_ACCOUNT_NAME_ERROR)
            is InvalidUserException -> createResponse(ErrorCodes.INVALID_USER_EXCEPTION)
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
            val userId = userService.getUserByAccountName(userEmail).userId

            slackInternalErrorSender.execute(cachingRequest, ex, userId)
        } catch (e: Exception) {
            slackInternalErrorSender.execute(cachingRequest, ex, null)
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
