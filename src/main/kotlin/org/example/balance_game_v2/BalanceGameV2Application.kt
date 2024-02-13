package org.example.balance_game_v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = ["org.example.balance_game_v2"],
)
class BalanceGameV2Application

fun main(args: Array<String>) {
    runApplication<BalanceGameV2Application>(*args)
}
