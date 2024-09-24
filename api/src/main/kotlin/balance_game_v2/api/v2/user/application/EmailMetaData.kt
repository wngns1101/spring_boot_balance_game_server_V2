package balance_game_v2.api.v2.user.application

interface EmailMetaData {
    companion object {
        const val AUTH_CODE_TEMPLATE = "auth_code_template"
        const val ACCOUNT_NAME_TEMPLATE = "account_name_template"
        const val TEMPORARY_PASSWORD_TEMPLATE = "temporary_password_template"

        const val CERTIFICATE_EMAIL_TITLE = "이메일 인증 메일입니다."
        const val ACCOUNT_NAME_AUTH_CODE_TITLE = "아이디 찾기 인증번호 메일입니다."
        const val TEMPORARY_PASSWORD_TITLE = "임시 비밀번호 발급 메일입니다."
    }
}
