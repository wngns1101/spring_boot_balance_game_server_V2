package domain

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableRedisRepositories("redis.*.repository")
@Configuration
class RedisConfig
