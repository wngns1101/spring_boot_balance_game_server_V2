package domain.board.dto

import domain.board.entity.BoardContent

data class BoardContentDTO(
    val boardContentId: Long,
    val title: String,
    val photoUrl: String,
)

fun BoardContent.toDTO(): BoardContentDTO {
    return BoardContentDTO(
        boardContentId = boardContentId!!,
        title = title,
        photoUrl = photoUrl,
    )
}
