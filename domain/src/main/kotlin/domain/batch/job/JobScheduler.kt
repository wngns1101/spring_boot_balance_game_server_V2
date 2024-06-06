package domain.batch.job

import jakarta.annotation.PostConstruct
import org.quartz.*
import org.springframework.stereotype.Service

@Service
class JobScheduler(
    private val scheduler: Scheduler,
) {

    @PostConstruct
    fun run()
    {
//        runJob(
//            job = TestJob::class.java,
//            desc = "스케쥴링 테스트",
//            exp = "5 * * * * ?"
//        )
    }

    fun runJob(job: Class<out Job>, desc: String, params: Map<String, Any>? = null, exp: String) {
        val jobDetail = jobDetail(job, desc, params)

        if (scheduler.checkExists(jobDetail.key)) {
            scheduler.deleteJob(jobDetail.key)
        }

        scheduler.scheduleJob(jobDetail, jobTrigger(exp))
    }

    private fun jobDetail(job: Class<out Job>, desc: String, params: Map<String, Any>? = null): JobDetail {
        val jobDataMap = JobDataMap()
        if (params != null) jobDataMap.putAll(params)

        return JobBuilder
            .newJob(job)
            .setJobData(jobDataMap)
            .withDescription(desc)
            .build()

    }

    private fun jobTrigger(exp: String): Trigger {
        return TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(exp)).build()
    }

}