package balance_game_v2.api.v2.user.http.res

import balance_game_v2.domain.auth.BlockReasonDTO

data class CheckBlockReasonResponseDTO(
    val reason: BlockReasonDTO
)
