package balance_game_v2.application

import balance_game_v2.api.http.req.ModifyReviewRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.dto.BoardReviewDetailDTO
import balance_game_v2.domain.board.dto.PageBoardDTO
import balance_game_v2.domain.board.dto.PageBoardReviewDTO
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardBackofficeFacade(
    private val boardService: BoardService,
) {
    fun getBoards(query: String?, adjustedPage: Int, size: Int, sortCondition: BoardSortCondition?, themeId: Long?, userId: Long?): PageBoardDTO {
        return boardService.getBoards(query, adjustedPage, size, sortCondition, null, null)
    }

    fun getBoardDetail(boardId: Long): Any {
        TODO("Not yet implemented")
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
}
