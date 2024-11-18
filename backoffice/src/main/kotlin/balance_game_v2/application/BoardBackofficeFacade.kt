package balance_game_v2.application

import balance_game_v2.api.http.req.ModifyBoardRequestDTO
import balance_game_v2.api.http.req.ModifyReviewRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.dto.BoardDetailByAdminDTO
import balance_game_v2.domain.board.dto.BoardReportDTO
import balance_game_v2.domain.board.dto.BoardReviewDetailDTO
import balance_game_v2.domain.board.dto.BoardReviewReportDTO
import balance_game_v2.domain.board.dto.PageBoardDTO
import balance_game_v2.domain.board.dto.PageBoardReportDTO
import balance_game_v2.domain.board.dto.PageBoardReviewDTO
import balance_game_v2.domain.board.dto.PageBoardReviewReportDTO
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardBackofficeFacade(
    private val boardService: BoardService,
) {
    fun getBoards(query: String?, adjustedPage: Int, size: Int, sortCondition: BoardSortCondition?, themeId: Long?, userId: Long?): PageBoardDTO {
        return boardService.getBoards(query, adjustedPage, size, sortCondition, null, null)
    }

    fun getBoardDetailByAdmin(boardId: Long): BoardDetailByAdminDTO {
        return boardService.getBoardDetailByAdmin(boardId)
    }

    fun getReviews(query: String?, page: Int, size: Int): PageBoardReviewDTO {
        return boardService.getReviewsByAdmin(query, page, size)
    }

    fun getBoardReviewDetail(boardReviewId: Long): BoardReviewDetailDTO {
        return boardService.getReviewDetail(boardReviewId)
    }

    fun modifyBoardReview(request: ModifyReviewRequestDTO) {
        return boardService.modifyBoardReview(request.toCommand())
    }

    fun deleteBoardReview(boardId: Long, boardReviewId: Long) {
        return boardService.deleteBoardReview(boardId, boardReviewId)
    }

    fun getBoardReports(query: String?, page: Int, size: Int): PageBoardReportDTO {
        return boardService.getBoardReports(query, page, size)
    }

    fun getBoardReviewReports(query: String?, page: Int, size: Int): PageBoardReviewReportDTO {
        return boardService.getBoardReviewReports(query, page, size)
    }

    fun getBoardReportDetail(boardReportId: Long): BoardReportDTO {
        return boardService.getBoardReportDetail(boardReportId)
    }

    fun getBoardReviewReportDetail(boardReviewReportId: Long): BoardReviewReportDTO {
        return boardService.getBoardReviewReportDetail(boardReviewReportId)
    }

    fun modifyBoard(boardId: Long, request: ModifyBoardRequestDTO) {
        return boardService.modifyBoardByAdmin(boardId, request.toCommand())
    }

    fun deleteBoard(boardId: Long) {
        return boardService.deleteBoardByAdmin(boardId)
    }
}
