package domain.board.dto

import domain.board.entity.BoardKeyword

class BoardKeywordDTO (
    val boardKeywordId: Long,
    val boardId: Long,
    val keyword: String,
)

fun BoardKeyword.toDTO(): BoardKeywordDTO {
    return BoardKeywordDTO(
        boardKeywordId = boardKeywordId!!,
        boardId = boardId,
        keyword = keyword
    )
}