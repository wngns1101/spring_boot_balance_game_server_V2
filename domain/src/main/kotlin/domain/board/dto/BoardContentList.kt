package domain.domain.board.dto

import domain.board.dto.BoardContentDTO

data class BoardContentList(
    val boardContentsDTO: List<BoardContentDTO>,
    val isReviewExist: Boolean,
)
