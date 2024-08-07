package domain.board.dto

import domain.board.entity.Board
import java.time.LocalDateTime

data class PageBoardDTO(
    val boards: List<BoardListDTO>,
    val totalPage: Int,
)

data class BoardListDTO(
    val boardId: Long,
    val title: String,
    val viewCount: Int,
    val likeCount: Int,
    val dislikeCount: Int,
    val updatedAt: LocalDateTime,
    val keywords: List<String>,
    val introduce: String,
)

fun Board.toPageBoardDTO(boardKeywords: List<BoardKeywordDTO>): BoardListDTO {
    return BoardListDTO(
        boardId = boardId!!,
        title = title,
        viewCount = viewCount,
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        updatedAt = updatedAt,
        keywords = boardKeywords.map { it.keyword },
        introduce = introduce
    )
}
