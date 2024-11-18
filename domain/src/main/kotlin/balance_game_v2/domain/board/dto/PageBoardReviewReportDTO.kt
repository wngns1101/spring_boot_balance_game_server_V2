package balance_game_v2.domain.board.dto

data class PageBoardReviewReportDTO(
    val boardReviewReports: List<BoardReviewReportDTO>,
    val totalPage: Int,
)
