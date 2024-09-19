package balance_game_v2.api.v1.user.http.req

import domain.domain.user.dto.WithDrawCommandDTO

data class WithDrawRequestDTO(
    val reason: String,
) {
    fun toCommand(): WithDrawCommandDTO {
        return WithDrawCommandDTO(
            reason = reason,
        )
    }
}
