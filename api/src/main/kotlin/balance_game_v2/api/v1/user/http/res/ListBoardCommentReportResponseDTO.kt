package balance_game_v2.api.v1.user.http.res

import domain.board.dto.BoardReviewReportDTO

data class ListBoardCommentReportResponseDTO(
    val boardCommentReports: List<BoardReviewReportDTO>
)
