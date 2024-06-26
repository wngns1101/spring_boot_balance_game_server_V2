package balance_game_v2.api.v1.user

import balance_game_v2.api.v1.user.application.TokenManager
import balance_game_v2.api.v1.user.application.UserFacade
import balance_game_v2.api.v1.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v1.user.http.req.ChangePasswordRequestDTO
import balance_game_v2.api.v1.user.http.req.ModifyUserInfoRequestDTO
import balance_game_v2.api.v1.user.http.req.SignInRequestDTO
import balance_game_v2.api.v1.user.http.req.SignUpRequestDTO
import balance_game_v2.api.v1.user.http.res.ListBoardCommentReportResponseDTO
import balance_game_v2.api.v1.user.http.res.ListBoardReportResponseDTO
import balance_game_v2.api.v1.user.http.res.ListUserNotificationResponseDTO
import balance_game_v2.api.v1.user.http.res.ListUserReportResponseDTO
import balance_game_v2.api.v1.user.http.res.ModifyUserNotificationResponseDTO
import balance_game_v2.api.v1.user.http.res.PageUserNotificationResponseDTO
import balance_game_v2.api.v1.user.http.res.ReIssueResponseDTO
import balance_game_v2.api.v1.user.http.res.SignInResponseDTO
import balance_game_v2.api.v1.user.http.res.SignUpResponseDTO
import balance_game_v2.api.v1.user.http.res.UserDetailResponseDTO
import balance_game_v2.config.USER_V2
import balance_game_v2.config.USER_V2_PREFIX
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = USER_V2)
@RestController
@RequestMapping(USER_V2_PREFIX)
class UserApiController(
    private val userFacade: UserFacade,
    private val tokenManager: TokenManager,
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
        return SignInResponseDTO(
            userFacade.signIn(
                signInRequestDTO.email,
                signInRequestDTO.password,
                signInRequestDTO.pushToken
            )
        )
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

        userFacade.changeUserPassword(
            user.email,
            changePasswordRequestDTO.currentPassword,
            changePasswordRequestDTO.newPassword
        )
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

    @Operation(summary = "[회원-009] 유저 알림 내역 리스트 조회")
    @GetMapping("/users/me/notifications")
    fun getUserNotifications(
        @RequestAttribute("email") email: String,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): PageUserNotificationResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return PageUserNotificationResponseDTO(userFacade.getUserNotificationHistories(user.userId, page, size))
    }

    @Operation(summary = "[회원-010] 유저 알림 읽음 처리")
    @PostMapping("/users/me/notifications/{notificationId}")
    fun readUserNotification(
        @RequestAttribute("email") email: String,
        @PathVariable("notificationId") notificationId: Long,
    ) {
        val user = userFacade.getUserByEmail(email)
        userFacade.readUserNotification(user.userId, notificationId)
    }

    @Operation(summary = "[회원-012] 유저 알림 설정 조회")
    @GetMapping("/users/me/marketing")
    fun getUserNotifications(
        @RequestAttribute("email") email: String,
    ): ListUserNotificationResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return ListUserNotificationResponseDTO(userFacade.getUserNotifications(user.userId))
    }

    @Operation(summary = "[회원-013] 유저 알림 수정")
    @PostMapping("/users/me/marketing/{userNotificationId}")
    fun modifyUserNotification(
        @RequestAttribute("email") email: String,
        @PathVariable("userNotificationId") userNotificationId: Long,
    ): ModifyUserNotificationResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return ModifyUserNotificationResponseDTO(userFacade.modifyUserNotification(user.userId, userNotificationId))
    }

    @Operation(summary = "[회원-014] 유저 초대코드 조회")
    @GetMapping("/users/me/invitation")
    fun getUserInvitation(
        @RequestAttribute("email") email: String,
    ) {
        val user = userFacade.getUserByEmail(email)
//       TODO: 초대 이력 가져오는 로직 추가
    }

    @Operation(summary = "[회원-015] 유저 신고")
    @PostMapping("/users/{userId}/report")
    fun createUserReport(
        @RequestAttribute("email") email: String,
        @PathVariable("userId") targetUserId: Long,
    ) {
        val user = userFacade.getUserByEmail(email)
        userFacade.createUserReport(user.userId, targetUserId)
    }

    @Operation(summary = "[회원-016] 신고한 유저 내역 조회")
    @GetMapping("/users/me/userReports")
    fun getUserReports(
        @RequestAttribute("email") email: String,
    ): ListUserReportResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return ListUserReportResponseDTO(userFacade.getUserReports(user.userId))
    }

    @Operation(summary = "[회원-017] 신고한 게시글 내역 조회")
    @GetMapping("/users/me/boardReports")
    fun getBoardReports(
        @RequestAttribute("email") email: String,
    ): ListBoardReportResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return ListBoardReportResponseDTO(userFacade.getBoardReports(user.userId))
    }

    @Operation(summary = "[회원-018] 신고한 댓글 내역 조회")
    @GetMapping("/users/me/boardCommentReports")
    fun getBoardCommentReports(
        @RequestAttribute("email") email: String,
    ): ListBoardCommentReportResponseDTO {
        val user = userFacade.getUserByEmail(email)
        return ListBoardCommentReportResponseDTO(userFacade.getBoardCommentReports(user.userId))
    }

    @Operation(summary = "[회원-019] 토큰 재발급")
    @PostMapping("/users/me/re-issue")
    fun getReIssue(
        req: HttpServletRequest,
        res: HttpServletResponse,
    ): ReIssueResponseDTO {
        var token = req.getHeader("Authorization")
        println("여긴?")
        println(token)
        token = try {
            token.split(" ")[1]
        } catch (e: InvalidTokenTypeException) {
            throw InvalidTokenTypeException()
        }

        return ReIssueResponseDTO(userFacade.reIssue(token))
    }
}
