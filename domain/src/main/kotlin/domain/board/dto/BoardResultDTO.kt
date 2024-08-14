package domain.board.dto

import domain.domain.board.dto.BoardContentItemDTO

data class BoardResultDTO(
    val boardContentId: Long,
    val title: String,
    val boardContentItems: List<BoardContentItemDTO>
)
