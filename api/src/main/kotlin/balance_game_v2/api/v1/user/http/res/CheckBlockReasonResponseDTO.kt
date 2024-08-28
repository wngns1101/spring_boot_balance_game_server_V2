package balance_game_v2.api.v1.user.http.res

import domain.auth.BlockReasonDTO

data class CheckBlockReasonResponseDTO(
    val reason: BlockReasonDTO
)
