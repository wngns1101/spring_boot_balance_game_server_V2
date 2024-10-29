package balance_game_v2.domain.board.dto

data class BoardResultDTO(
    val boardContentId: Long,
    val title: String?,
    val boardContentItems: List<BoardContentItemDTO>
)
