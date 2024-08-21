package balance_game_v2.api.v1.board.application

import balance_game_v2.api.v1.board.http.req.BoardModifyRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardResultRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.req.DeleteBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.req.EvaluationBoardRequest
import balance_game_v2.api.v1.board.http.req.ModifyBoardReviewRequestDTO
import balance_game_v2.api.v1.board.http.req.toCommand
import domain.board.BoardService
import domain.board.dto.BoardContentDTO
import domain.board.dto.BoardDetailDTO
import domain.board.dto.BoardListDTO
import domain.board.dto.BoardResultDTO
import domain.board.dto.PageBoardDTO
import domain.board.model.BoardSortCondition
import domain.domain.board.dto.BoardEvaluationDTO
import domain.domain.board.dto.SimpleBoardDTO
import org.springframework.stereotype.Component

@Component
class BoardFacade(
    val boardService: BoardService,
) {

    fun createBoard(userId: Long, request: CreateBoardRequestDTO) {
        boardService.createBoard(userId, request.toCommand())
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?, themeId: Long?): PageBoardDTO {
        return boardService.getBoards(query, page, size, sortCondition, themeId)
    }

    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        return boardService.getBoardDetail(boardId)
    }

    fun boardEvaluation(boardId: Long, userId: Long, request: EvaluationBoardRequest): BoardEvaluationDTO {
        return boardService.boardEvaluation(boardId, userId, request.toCommand())
    }

    fun getBoardContents(boardId: Long): List<BoardContentDTO> {
        return boardService.getBoardContents(boardId)
    }

    fun createBoardResult(boardId: Long, request: List<CreateBoardResultRequestDTO>, userId: Long) {
        boardService.createBoardResult(boardId, request.map { it.toCommand() }, userId)
    }

    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
        return boardService.getBoardResult(boardId)
    }

    fun modifyBoard(boardId: Long, request: BoardModifyRequestDTO) {
        return boardService.modifyBoard(boardId, request.toCommand())
    }

    fun createBoardReview(boardId: Long, userId: Long, request: CreateBoardReviewRequestDTO) {
        return boardService.createBoardReview(boardId, userId, request.toCommand())
    }

    fun modifyBoardReview(boardId: Long, userId: Long, request: ModifyBoardReviewRequestDTO) {
        return boardService.modifyBoardReview(boardId, userId, request.toCommand())
    }

    fun deleteBoardReview(userId: Long, request: DeleteBoardReviewRequestDTO) {
        return boardService.deleteBoardReview(userId, request.toCommand())
    }

    fun createBoardReport(boardId: Long, userId: Long) {
        boardService.createBoardReport(boardId, userId)
    }

    fun createBoardCommentReport(boardCommentId: Long, userId: Long) {
        boardService.createBoardCommentReport(boardCommentId, userId)
    }

    fun todayRecommendGame(): SimpleBoardDTO {
        return boardService.todayRecommendGame()
    }

    fun getMyBoardCount(userId: Long): Int {
        return boardService.getMyBoardCount(userId)
    }

    fun getMyBoards(userId: Long): List<BoardListDTO> {
        return boardService.getMyBoards(userId)
    }

    fun relatedBoards(boardId: Long): List<SimpleBoardDTO> {
        return boardService.relatedBoards(boardId)
    }
}
