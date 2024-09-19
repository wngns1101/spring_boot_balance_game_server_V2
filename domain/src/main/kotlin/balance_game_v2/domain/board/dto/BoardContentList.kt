package balance_game_v2.domain.board.dto

data class BoardContentList(
    val boardContentsDTO: List<BoardContentDTO>,
    val isReviewExist: Boolean,
)
