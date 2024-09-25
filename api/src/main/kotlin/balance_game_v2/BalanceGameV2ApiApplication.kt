package balance_game_v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.scheduling.annotation.EnableAsync

@EnableRedisRepositories("balance_game_v2.redis.*")
@SpringBootApplication(
    scanBasePackages = ["balance_game_v2.api", "balance_game_v2.domain"],
)
@EnableAsync
class BalanceGameV2ApiApplication

fun main(args: Array<String>) {
    runApplication<BalanceGameV2ApiApplication>(*args)
}
