package domain.domain.board.dto

import domain.board.entity.Board

data class ParticipatedBoardDTO(
    val title: String,
    val keywords: List<String>,
    val introduce: String,
    val isReviewExist: Boolean,
)

fun Board.toParticipatedBoardDTO(isReviewExist: Boolean, keywords: List<String>): ParticipatedBoardDTO {
    return ParticipatedBoardDTO(
        title = title,
        keywords = keywords,
        introduce = introduce,
        isReviewExist = isReviewExist,
    )
}
