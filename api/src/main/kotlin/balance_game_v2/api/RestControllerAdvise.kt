package balance_game_v2.api

import balance_game_v2.api.support.error.ErrorModel
import domain.auth.exception.NotSignUpUserException
import domain.auth.exception.PasswordMismatchException
import domain.error.AlreadyExistEmailException
import domain.error.AlreadySignUpException
import domain.error.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestControllerAdvise {
    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorModel> {
        val errorMessage = ErrorModel(
            code = HttpStatus.NOT_FOUND.value(),
            message = ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ErrorModel> {
        return when (ex) {
            is AlreadyExistEmailException -> createResponse(ErrorModel(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다"), HttpStatus.BAD_REQUEST)
            is AlreadySignUpException -> createResponse(ErrorModel(HttpStatus.BAD_REQUEST.value(), "이미 가입한 유저입니다."), HttpStatus.BAD_REQUEST)
            is NotSignUpUserException -> createResponse(ErrorModel(HttpStatus.BAD_REQUEST.value(), "가입되지 않은 유저입니다"), HttpStatus.BAD_REQUEST)
            is PasswordMismatchException -> createResponse(ErrorModel(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다"), HttpStatus.BAD_REQUEST)
            else -> createResponse(ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
fun createResponse(error: ErrorModel, httpStatus: HttpStatus): ResponseEntity<ErrorModel> {
    return ResponseEntity(error, httpStatus)
}
