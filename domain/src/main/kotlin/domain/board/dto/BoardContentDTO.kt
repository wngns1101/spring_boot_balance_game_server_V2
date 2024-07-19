package domain.board.dto

import domain.board.entity.BoardContent

data class BoardContentDTO(
    val boardContentId: Long,
    val title: String,
)

fun BoardContent.toDTO(): BoardContentDTO {
    return BoardContentDTO(
        boardContentId = boardContentId!!,
        title = title,
    )
}
