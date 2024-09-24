package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.Board

data class ParticipatedBoardDTO(
    val boardId: Long,
    val title: String,
    val keywords: List<String>,
    val introduce: String,
    val isReviewExist: Boolean,
)

fun Board.toParticipatedBoardDTO(isReviewExist: Boolean, keywords: List<String>): ParticipatedBoardDTO {
    return ParticipatedBoardDTO(
        boardId = boardId!!,
        title = title,
        keywords = keywords,
        introduce = introduce,
        isReviewExist = isReviewExist,
    )
}
