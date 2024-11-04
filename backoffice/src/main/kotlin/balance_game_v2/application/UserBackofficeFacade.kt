package balance_game_v2.application

import balance_game_v2.api.http.req.ModifyUserRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.auth.AuthService
import balance_game_v2.domain.user.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class UserBackofficeFacade(
    private val userService: UserService,
    private val authService: AuthService,
) {
    fun postProfileByAdmin(file: MultipartFile): String {
        return userService.postProfile(file)
    }

    @Transactional
    fun modifyUserByAdmin(request: ModifyUserRequestDTO) {
        userService.modifyUserByAdmin(request.toCommand())
        authService.modifyAuthStatus(request.authId, request.accountName, request.authStatus)
    }
}
