package balance_game_v2.api.v1.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import balance_game_v2.api.v1.user.application.UserFacade
import balance_game_v2.api.v1.user.http.req.*
import balance_game_v2.api.v1.user.http.res.SignInResponseDTO
import balance_game_v2.api.v1.user.http.res.SignUpResponseDTO
import balance_game_v2.api.v1.user.http.res.UserDetailResponseDTO
import balance_game_v2.config.USER_V2
import balance_game_v2.config.USER_V2_PREFIX
import org.springframework.web.bind.annotation.*

@Tag(name = USER_V2)
@RestController
@RequestMapping(USER_V2_PREFIX)
class UserApiController(
    private val userFacade: UserFacade
) {
    @Operation(summary = "[회원-001] 회원가입")
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody signUpRequestDTO: SignUpRequestDTO
    ): SignUpResponseDTO {
        return SignUpResponseDTO(userFacade.signUp(signUpRequestDTO.toCommand(signUpRequestDTO)))
    }

    @Operation(summary = "[회원-002] 로그인")
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody signInRequestDTO: SignInRequestDTO
    ): SignInResponseDTO {
        return SignInResponseDTO(userFacade.signIn(signInRequestDTO.email, signInRequestDTO.password, signInRequestDTO.pushToken))
    }

    @Operation(summary = "[회원-003] 회원 상세")
    @GetMapping("/users/me")
    fun userDetail(
        @RequestAttribute("email") email: String
    ): UserDetailResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return UserDetailResponseDTO(user)
    }

    @Operation(summary = "[회원-004] 회원 비밀번호 변경")
    @PostMapping("/users/me/change-password")
    fun changeUserPassword(
        @RequestAttribute("email") email: String,
        @RequestBody changePasswordRequestDTO: ChangePasswordRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        userFacade.changeUserPassword(user.email, changePasswordRequestDTO.currentPassword, changePasswordRequestDTO.newPassword)
    }

    @Operation(summary = "[회원-005] 회원 정보 수정")
    @PutMapping("/users/me")
    fun modifyUserInfo(
        @RequestAttribute("email") email: String,
        @RequestBody modifyUserInfoRequestDTO: ModifyUserInfoRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        userFacade.modifyUserInfo(user.userId, modifyUserInfoRequestDTO.nickName, modifyUserInfoRequestDTO.phoneNumber)
    }

    @Operation(summary = "[회원-006] 비밀번호 찾기")
    fun getUserPassword() {
//        TODO: 비밀번호 찾기 추가
    }

    @Operation(summary = "[회원-007] 이메일 찾기")
    fun getUserEmail() {
//        TODO: 이메일 찾기 추가
    }

    @Operation(summary = "[회원-008] 회원 탈퇴")
    @PostMapping("/users/me/withdraw")
    fun withdraw(
        @RequestAttribute("email") email: String,
    ) {
        val user = userFacade.getUserByEmail(email)
        userFacade.withdraw(user.userId)
    }

    @Operation(summary = "[회원-009] 유저 알림 리스트 조회")
    @GetMapping("/users/me/notifications")
    fun getUserNotifications(
        @RequestAttribute("email") email: String,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): PageUserNotificationResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return PageUserNotificationResponseDTO(userFacade.getUserNotifications(user.userId, page, size))
    }

    @Operation(summary = "[회원-010] 유저 알림 읽음 처리")
    @PostMapping("/users/me/notifications/{userNotificationId}")
    fun readUserNotification(
        @RequestAttribute("email") email: String,
        @PathVariable("userNotificationId") userNotificationId: Long,
    ) {
        val user = userFacade.getUserByEmail(email)
        userFacade.readUserNotification(user.userId, userNotificationId)
    }
}
