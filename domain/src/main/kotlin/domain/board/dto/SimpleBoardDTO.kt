package domain.domain.board.dto

import domain.board.entity.Board

data class SimpleBoardDTO(
    val boardId: Long,
    val title: String,
    val introduce: String,
)

fun Board.toSimpleBoard(): SimpleBoardDTO {
    return SimpleBoardDTO(
        boardId = boardId!!,
        title = title,
        introduce = introduce,
    )
}
