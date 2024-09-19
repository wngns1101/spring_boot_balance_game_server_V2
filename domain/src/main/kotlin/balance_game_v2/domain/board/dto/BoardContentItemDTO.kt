package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardContentItem

data class BoardContentItemDTO(
    val boardContentItemId: Long,
    val boardContentId: Long,
    val item: String,
    val boardResultCount: Int,
)

fun BoardContentItem.toDTO(boardResultCount: Int): BoardContentItemDTO {
    return BoardContentItemDTO(
        boardContentItemId = boardContentItemId!!,
        boardContentId = boardContentId,
        item = item,
        boardResultCount = boardResultCount
    )
}
