package domain.batch.job

import domain.auth.model.AuthStatus
import domain.auth.repository.AuthRepository
import domain.user.UserService
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.scheduling.quartz.QuartzJobBean
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WithDrawJob(
    private val userService: UserService,
    private val authRepository: AuthRepository,
) : QuartzJobBean() {

    val log = LoggerFactory.getLogger(WithDrawJob::class.java)

    override fun executeInternal(context: JobExecutionContext) {
        log.info("회원 탈퇴 처리 시작")
        val auths = authRepository.findByStatus(AuthStatus.DELETE)

        val filterAuths = auths.filter {
            ChronoUnit.DAYS.between(it.deletedAt, LocalDateTime.now()) >= 30
        }

        val accountNames = filterAuths.map { it.accountName }
        userService.withdraw(accountNames)

        log.info("회원 탈퇴 처리 종료")
    }
}
