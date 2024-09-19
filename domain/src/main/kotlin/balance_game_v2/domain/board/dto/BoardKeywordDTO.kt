package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardKeyword

class BoardKeywordDTO(
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
