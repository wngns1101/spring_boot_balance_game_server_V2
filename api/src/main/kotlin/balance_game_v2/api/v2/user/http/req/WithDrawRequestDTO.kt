package balance_game_v2.api.v2.user.http.req

import balance_game_v2.domain.user.dto.WithDrawCommandDTO

data class WithDrawRequestDTO(
    val reason: String,
) {
    fun toCommand(): WithDrawCommandDTO {
        return WithDrawCommandDTO(
            reason = reason,
        )
    }
}
