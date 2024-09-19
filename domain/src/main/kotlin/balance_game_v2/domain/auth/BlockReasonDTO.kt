package balance_game_v2.domain.auth

import balance_game_v2.domain.user.entity.BlockUserHistory

data class BlockReasonDTO(
    val blockUserHistoryId: Long,
    val adminName: String,
    val userId: Long,
    val reason: String,
)

fun BlockUserHistory.toDTO(adminName: String): BlockReasonDTO {
    return BlockReasonDTO(
        blockUserHistoryId = blockUserHistoryId!!,
        adminName = adminName,
        userId = userId,
        reason = reason,
    )
}
