package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardContent

data class BoardContentDTO(
    val boardContentId: Long,
    val title: String?,
    val boardContentItems: List<BoardContentItemDTO>
)

fun BoardContent.toDTO(boardContentItems: List<BoardContentItemDTO>): BoardContentDTO {
    return BoardContentDTO(
        boardContentId = boardContentId!!,
        title = title,
        boardContentItems = boardContentItems
    )
}
