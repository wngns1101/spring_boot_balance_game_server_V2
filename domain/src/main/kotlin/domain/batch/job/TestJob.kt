package domain.batch.job

import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

import org.springframework.scheduling.quartz.QuartzJobBean

class TestJob : QuartzJobBean() {
    val log = LoggerFactory.getLogger(TestJob::class.java)

    override fun executeInternal(context: JobExecutionContext) {
        log.info("배치 테스트")
    }
}