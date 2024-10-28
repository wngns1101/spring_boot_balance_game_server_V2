package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.Board

data class SimpleBoardDTO(
    val boardId: Long,
    val title: String,
    val introduce: String,
    val likeCount: Int?,
)

fun Board.toSimpleBoard(): SimpleBoardDTO {
    return SimpleBoardDTO(
        boardId = boardId!!,
        title = title,
        introduce = introduce,
        likeCount = likeCount,
    )
}
