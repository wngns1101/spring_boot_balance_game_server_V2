package redis.certificate.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("certificate")
class Certificate(
    @Id
    val email: String,
    val code: String,
)
