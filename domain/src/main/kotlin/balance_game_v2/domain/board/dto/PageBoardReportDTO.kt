package balance_game_v2.domain.board.dto

data class PageBoardReportDTO(
    val boardReports: List<BoardReportDTO>,
    val totalPage: Int,
)
