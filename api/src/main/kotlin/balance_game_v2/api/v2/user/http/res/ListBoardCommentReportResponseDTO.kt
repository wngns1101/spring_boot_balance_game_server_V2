package balance_game_v2.api.v2.user.http.res

import balance_game_v2.domain.board.dto.BoardReviewReportDTO

data class ListBoardCommentReportResponseDTO(
    val boardCommentReports: List<BoardReviewReportDTO>
)
