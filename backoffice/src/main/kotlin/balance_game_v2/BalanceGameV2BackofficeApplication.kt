package balance_game_v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class BalanceGameV2BackofficeApplication

fun main(args: Array<String>) {
    runApplication<BalanceGameV2BackofficeApplication>(*args)
}
