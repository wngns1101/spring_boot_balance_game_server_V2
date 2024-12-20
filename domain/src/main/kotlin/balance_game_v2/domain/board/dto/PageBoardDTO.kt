package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.Board
import java.time.LocalDateTime

data class PageBoardDTO(
    val boards: List<BoardListDTO>,
    val totalPage: Int,
)

data class BoardListDTO(
    val boardId: Long,
    val themeId: Long,
    val userId: Long,
    val title: String,
    val viewCount: Int,
    val likeCount: Int,
    val dislikeCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
    val keywords: List<String>,
    val introduce: String,
)

fun Board.toPageBoardDTO(boardKeywords: List<BoardKeywordDTO>): BoardListDTO {
    return BoardListDTO(
        boardId = boardId!!,
        themeId = themeId,
        userId = userId,
        title = title,
        viewCount = viewCount,
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
        keywords = boardKeywords.map { it.keyword },
        introduce = introduce
    )
}
