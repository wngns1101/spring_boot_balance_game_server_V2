package balance_game_v2.api.v1.user.application

interface EmailMetaData {
    companion object {
        const val TEMPLATE = "auth_code_template"

        const val CERTIFICATE_EMAIL_TITLE = "이메일 인증 메일입니다."
        const val ACCOUNT_NAME_AUTH_CODE_TITLE = "아이디 찾기 인증번호 메일입니다."
        const val PASSWORD_ACCOUNT_AUTH_CODE_TITLE = "비밀번호 재설정 인증번호 메일입니다."
    }
}
