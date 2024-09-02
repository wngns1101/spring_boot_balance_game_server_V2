package domain.board.dto

import domain.board.entity.BoardContent
import domain.domain.board.dto.BoardContentItemDTO

data class BoardContentDTO(
    val boardContentId: Long,
    val title: String,
    val boardContentItems: List<BoardContentItemDTO>
)

fun BoardContent.toDTO(boardContentItems: List<BoardContentItemDTO>): BoardContentDTO {
    return BoardContentDTO(
        boardContentId = boardContentId!!,
        title = title,
        boardContentItems = boardContentItems
    )
}
