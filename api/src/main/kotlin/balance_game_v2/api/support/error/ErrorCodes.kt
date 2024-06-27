package balance_game_v2.api.support.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(val httpStatus: HttpStatus, val message: String) {
    INVALID_TOKEN_TYPE_ERROR(HttpStatus.UNAUTHORIZED, "토큰 타입 오류"),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "토큰 만료"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류"),
    ALREADY_EXIST_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이미 가입된 이메일"),
    ALREADY_SIGN_UP_ERROR(HttpStatus.BAD_REQUEST, "이미 가입된 유저"),
    NOT_SIGN_UP_USER_ERROR(HttpStatus.BAD_REQUEST, "가입되지 않은 유저"),
    PASSWORD_MISMATCH_ERROR(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    NOT_EQUALS_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "토큰이 일치하지 않습니다")
}
