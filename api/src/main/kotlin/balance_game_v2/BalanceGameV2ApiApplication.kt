package balance_game_v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication(
    scanBasePackages = ["balance_game_v2", "domain", "redis"]
)
@EnableAsync
class BalanceGameV2ApiApplication

fun main(args: Array<String>) {
    runApplication<BalanceGameV2ApiApplication>(*args)
}
