package balance_game_v2.api.support.error

import org.springframework.http.HttpStatus

enum class ErrorCodes(val httpStatus: HttpStatus, val message: String) {
    INVALID_TOKEN_TYPE_ERROR(HttpStatus.UNAUTHORIZED, "토큰 타입 오류"),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "토큰 만료"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류"),
    ALREADY_EXIST_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    ALREADY_EXIST_ACCOUNT_NAME_ERROR(HttpStatus.BAD_REQUEST, "이미 가입된 아이디입니다."),
    ALREADY_SIGN_UP_ERROR(HttpStatus.BAD_REQUEST, "이미 가입된 유저입니다."),
    NOT_SIGN_UP_USER_ERROR(HttpStatus.BAD_REQUEST, "가입되지 않은 유저입니다."),
    PASSWORD_MISMATCH_ERROR(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_EQUALS_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "토큰이 일치하지 않습니다."),
    NOT_EXIST_TOKEN_ERROR(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
    WITH_DRAW_USER_ERROR(HttpStatus.BAD_REQUEST, "탈퇴한 유저입니다."),
    BLOCK_USER_ERROR(HttpStatus.BAD_REQUEST, "차단된 유저입니다."),
    NOT_SIGNED_GAME_ERROR(HttpStatus.BAD_REQUEST, "아직 게임에 참여하지 않았습니다."),
    ALREADY_EXIST_REVIEW_ERROR(HttpStatus.BAD_REQUEST, "이미 작성된 리뷰가 있습니다."),
    NOT_FOUND_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이메일이 존재하지 않습니다."),
    NOT_FOUND_USER_ERROR(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다."),
    NOT_FOUND_BOARD_ERROR(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다."),
    NOT_FOUND_REVIEW_ERROR(HttpStatus.BAD_REQUEST, "리뷰가 존재하지 않습니다."),
    NOT_FOUND_ACCOUNT_NAME_ERROR(HttpStatus.BAD_REQUEST, "계정이 존재하지 않습니다."),
    INVALID_USER_EXCEPTION(HttpStatus.BAD_REQUEST, "유저가 일치하지 않습니다"),
    NOT_FOUND_BOARD_REPORT_ERROR(HttpStatus.BAD_REQUEST, "신고된 게시글이 존재하지 않습니다."),
    NOT_FOUND_BOARD_REVIEW_REPORT_ERROR(HttpStatus.BAD_REQUEST, "신고된 리뷰가 존재하지 않습니다."),
    NOT_FOUND_BOARD_CONTENT_ITEM_ERROR(HttpStatus.BAD_REQUEST, "게임 답변이 존재하지 않습니다.")
}
