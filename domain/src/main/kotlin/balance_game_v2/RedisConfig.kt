
package domain.balance_game_v2

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableRedisRepositories("balance_game_v2.redis.*.repository")
@Configuration
class RedisConfig
