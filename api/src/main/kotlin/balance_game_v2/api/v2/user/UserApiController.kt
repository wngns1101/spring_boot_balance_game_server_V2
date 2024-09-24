package balance_game_v2.api.v2.user

import balance_game_v2.api.client.S3Config
import balance_game_v2.api.v2.board.application.BoardFacade
import balance_game_v2.api.v2.user.application.TokenManager
import balance_game_v2.api.v2.user.application.UserFacade
import balance_game_v2.api.v2.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v2.user.http.req.ChangePasswordRequestDTO
import balance_game_v2.api.v2.user.http.req.CheckEmailCertificateRequestDTO
import balance_game_v2.api.v2.user.http.req.CheckEmailRequestDTO
import balance_game_v2.api.v2.user.http.req.EmailCertificateRequestDTO
import balance_game_v2.api.v2.user.http.req.FindAccountNameRequestDTO
import balance_game_v2.api.v2.user.http.req.ModifyUserInfoRequestDTO
import balance_game_v2.api.v2.user.http.req.SignInRequestDTO
import balance_game_v2.api.v2.user.http.req.SignUpRequestDTO
import balance_game_v2.api.v2.user.http.req.TemporaryPasswordRequestDTO
import balance_game_v2.api.v2.user.http.req.WithDrawRequestDTO
import balance_game_v2.api.v2.user.http.res.CheckBlockReasonResponseDTO
import balance_game_v2.api.v2.user.http.res.CheckEmailCertificateResponseDTO
import balance_game_v2.api.v2.user.http.res.CheckEmailResponseDTO
import balance_game_v2.api.v2.user.http.res.ListUserNotificationResponseDTO
import balance_game_v2.api.v2.user.http.res.MainUserInfoResponseDTO
import balance_game_v2.api.v2.user.http.res.ModifyUserNotificationResponseDTO
import balance_game_v2.api.v2.user.http.res.PageUserNotificationResponseDTO
import balance_game_v2.api.v2.user.http.res.ReIssueResponseDTO
import balance_game_v2.api.v2.user.http.res.SignInResponseDTO
import balance_game_v2.api.v2.user.http.res.SignUpResponseDTO
import balance_game_v2.api.v2.user.http.res.UserDetailResponseDTO
import balance_game_v2.config.USER_V2
import balance_game_v2.config.USER_V2_PREFIX
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = USER_V2)
@RestController
@RequestMapping(USER_V2_PREFIX)
class UserApiController(
    private val userFacade: UserFacade,
    private val tokenManager: TokenManager,
    private val s3Config: S3Config,
    private val boardFacade: BoardFacade,
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
                signInRequestDTO.accountName,
                signInRequestDTO.password,
                signInRequestDTO.pushToken
            )
        )
    }

    @Operation(summary = "[회원-003] 회원 상세")
    @GetMapping("/users/me")
    fun userDetail(
        @RequestAttribute("accountName") accountName: String
    ): UserDetailResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        return UserDetailResponseDTO(user)
    }

    @Operation(summary = "[회원-004] 회원 비밀번호 변경")
    @PostMapping("/users/me/change-password")
    fun changeUserPassword(
        @RequestAttribute("accountName") accountName: String,
        @RequestBody changePasswordRequestDTO: ChangePasswordRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        userFacade.changeUserPassword(
            user.accountName,
            changePasswordRequestDTO.currentPassword,
            changePasswordRequestDTO.newPassword
        )
    }

    @Operation(summary = "[회원-005] 회원 정보 수정")
    @PutMapping("/users/me")
    fun modifyUserInfo(
        @RequestAttribute("accountName") accountName: String,
        @RequestBody modifyUserInfoRequestDTO: ModifyUserInfoRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)

        userFacade.modifyUserInfo(user.userId, modifyUserInfoRequestDTO.nickName, modifyUserInfoRequestDTO.profileUrl)
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
        @RequestAttribute("accountName") accountName: String,
        @RequestBody request: WithDrawRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(accountName)
        userFacade.withdraw(user.userId, user.accountName, request.toCommand())
    }

    @Operation(summary = "[회원-009] 유저 알림 내역 리스트 조회")
    @GetMapping("/users/me/notifications")
    fun getUserNotifications(
        @RequestAttribute("accountName") accountName: String,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): PageUserNotificationResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return PageUserNotificationResponseDTO(userFacade.getUserNotificationHistories(user.userId, page, size))
    }

    @Operation(summary = "[회원-010] 유저 알림 읽음 처리")
    @PostMapping("/users/me/notifications/{notificationId}")
    fun readUserNotification(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("notificationId") notificationId: Long,
    ) {
        val user = userFacade.getUserByAccountName(accountName)
        userFacade.readUserNotification(user.userId, notificationId)
    }

    @Operation(summary = "[회원-012] 유저 알림 설정 조회")
    @GetMapping("/users/me/marketing")
    fun getUserNotifications(
        @RequestAttribute("accountName") accountName: String,
    ): ListUserNotificationResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return ListUserNotificationResponseDTO(userFacade.getUserNotifications(user.userId))
    }

    @Operation(summary = "[회원-013] 유저 알림 수정")
    @PostMapping("/users/me/marketing/{userNotificationId}")
    fun modifyUserNotification(
        @RequestAttribute("accountName") accountName: String,
        @PathVariable("userNotificationId") userNotificationId: Long,
    ): ModifyUserNotificationResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return ModifyUserNotificationResponseDTO(userFacade.modifyUserNotification(user.userId, userNotificationId))
    }

    @Operation(summary = "[회원-014] 유저 초대코드 조회")
    @GetMapping("/users/me/invitation")
    fun getUserInvitation(
        @RequestAttribute("accountName") accountName: String,
    ) {
        val user = userFacade.getUserByAccountName(accountName)
//       TODO: 초대 이력 가져오는 로직 추가
    }

    @Operation(summary = "[회원-019] 토큰 재발급")
    @PostMapping("/users/me/re-issue")
    fun getReIssue(
        req: HttpServletRequest,
        res: HttpServletResponse,
    ): ReIssueResponseDTO {
        var token = req.getHeader("Authorization")
        println(token)
        token = try {
            token.split(" ")[1]
        } catch (e: InvalidTokenTypeException) {
            throw InvalidTokenTypeException()
        }

        return ReIssueResponseDTO(userFacade.reIssue(token))
    }

    @Operation(summary = "[회원-020] 아이디 중복확인")
    @PostMapping("/check-account-name")
    fun getReIssue(
        @RequestBody req: CheckEmailRequestDTO,
    ): CheckEmailResponseDTO {
        return CheckEmailResponseDTO(userFacade.checkDuplicateEmail(req.accountName))
    }

    @Operation(summary = "[회원-021] 프로필 업로드")
    @PostMapping("/profile", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun profile(
        @RequestPart file: MultipartFile,
    ): String {
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = file.size
        }

        val objectKey = "profiles/${UUID.randomUUID()}-${file.originalFilename}"

        val putObjectRequest = PutObjectRequest(
            "balance-game-bucket",
            objectKey,
            file.inputStream,
            objectMetadata,
        )

        s3Config.amazonS3Client().putObject(putObjectRequest)

        val publicUrl = "https://balance-game-bucket.s3.amazonaws.com/$objectKey"

        return publicUrl
    }

    @Operation(summary = "[회원-022] 이메일 인증")
    @PostMapping("/email-certificate")
    fun send(
        @RequestBody request: EmailCertificateRequestDTO,
    ) {
        userFacade.sendAuthCodeForEmailCertificate(request.email, UUID.randomUUID().toString().substring(0, 8))
    }

    @Operation(summary = "[회원-023] 인증번호 확인")
    @PostMapping("/check-email-certificate")
    fun send(
        @RequestBody request: CheckEmailCertificateRequestDTO,
    ): CheckEmailCertificateResponseDTO {
        return CheckEmailCertificateResponseDTO(userFacade.checkEmailCertificate(request.email, request.code))
    }

    @Operation(summary = "[회원-024] 메인 유저 정보 조회")
    @GetMapping("/main/userInfo")
    fun getUserInfo(
        @RequestAttribute("accountName") accountName: String,
    ): MainUserInfoResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)

        val myBoardCount = boardFacade.getMyBoardCount(user.userId)

        return MainUserInfoResponseDTO(
            userId = user.userId,
            nickName = user.nickname,
            myBoardCount = myBoardCount
        )
    }

    @Operation(summary = "[회원-025] 토큰 유효성 확인")
    @GetMapping("/check-token-validation")
    fun checkTokenValidation(
        @RequestAttribute("accountName") accountName: String,
    ) {
        userFacade.checkTokenValidation(accountName)
    }

    @Operation(summary = "[회원-026] 차단 사유 확인")
    @GetMapping("/check-block-reason")
    fun checkBlockReason(
        @RequestAttribute("accountName") accountName: String,
    ): CheckBlockReasonResponseDTO {
        val user = userFacade.getUserByAccountName(accountName)
        return CheckBlockReasonResponseDTO(userFacade.checkBlockReason(user.userId))
    }

    @Operation(summary = "[회원-027] 아이디 찾기 발송")
    @PostMapping("/find-account-name")
    fun findAccountName(
        @RequestBody request: FindAccountNameRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(request.email)
        userFacade.sendAccountNameForEmail(user.email, user.accountName)
    }

    @Operation(summary = "[회원-028] 임시 비밀번호 발송")
    @PostMapping("/temporary-password")
    fun temporaryPassword(
        @RequestBody request: TemporaryPasswordRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(request.accountName)
        userFacade.sendTemporaryPasswordForAccountName(user.accountName, user.email)
    }
}
