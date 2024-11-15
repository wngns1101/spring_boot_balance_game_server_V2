package balance_game_v2.api.v2.board.application

import balance_game_v2.api.v2.board.http.req.BoardModifyRequestDTO
import balance_game_v2.api.v2.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v2.board.http.req.CreateBoardResultRequestDTO
import balance_game_v2.api.v2.board.http.req.CreateBoardReviewRequestDTO
import balance_game_v2.api.v2.board.http.req.ModifyBoardReviewRequestDTO
import balance_game_v2.api.v2.board.http.req.toCommand
import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.dto.BoardContentList
import balance_game_v2.domain.board.dto.BoardDetailDTO
import balance_game_v2.domain.board.dto.BoardListDTO
import balance_game_v2.domain.board.dto.BoardResultDTO
import balance_game_v2.domain.board.dto.BoardReviewDTO
import balance_game_v2.domain.board.dto.PageBoardDTO
import balance_game_v2.domain.board.dto.ParticipatedBoardDTO
import balance_game_v2.domain.board.dto.SimpleBoardDTO
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardFacade(
    val boardService: BoardService,
) {

    fun createBoard(userId: Long, request: CreateBoardRequestDTO) {
        boardService.createBoard(userId, request.toCommand())
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?, themeId: Long?, userId: Long?): PageBoardDTO {
        return boardService.getBoards(query, page, size, sortCondition, themeId, userId)
    }

    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        return boardService.getBoardDetail(boardId)
    }

//    fun boardEvaluation(boardId: Long, userId: Long, request: EvaluationBoardRequest): BoardEvaluationDTO {
//        return boardService.boardEvaluation(boardId, userId, request.toCommand())
//    }

    fun getBoardContents(boardId: Long, userId: Long): BoardContentList {
        return boardService.getBoardContents(boardId, userId)
    }

    fun createBoardResult(boardId: Long, request: List<CreateBoardResultRequestDTO>, userId: Long): List<BoardResultDTO> {
        val boardId = boardService.createBoardResult(boardId, request.map { it.toCommand() }, userId)
        return boardService.getBoardResult(boardId)
    }

//    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
//        return boardService.getBoardResult(boardId)
//    }

    fun modifyBoard(boardId: Long, request: BoardModifyRequestDTO) {
        return boardService.modifyBoard(boardId, request.toCommand())
    }

    fun createBoardReview(boardId: Long, userId: Long, request: CreateBoardReviewRequestDTO) {
        return boardService.createBoardReview(boardId, userId, request.toCommand())
    }

    fun modifyBoardReview(boardId: Long, userId: Long, request: ModifyBoardReviewRequestDTO) {
        return boardService.modifyBoardReview(boardId, userId, request.toCommand())
    }

    fun deleteBoardReview(userId: Long, boardId: Long, boardReviewId: Long) {
        return boardService.deleteBoardReview(userId, boardId, boardReviewId)
    }

    fun createBoardReport(boardId: Long, userId: Long, content: String) {
        boardService.createBoardReport(boardId, userId, content)
    }

    fun createBoardReviewReport(boardId: Long, boardReviewId: Long, userId: Long, content: String) {
        boardService.createBoardReviewReport(boardId, boardReviewId, userId, content)
    }

    fun todayRecommendGame(userId: Long?): SimpleBoardDTO {
        return boardService.todayRecommendGame(userId)
    }

    fun getMyBoardCount(userId: Long): Int {
        return boardService.getMyBoardCount(userId)
    }

    fun getMyBoards(userId: Long): List<BoardListDTO> {
        return boardService.getMyBoards(userId)
    }

    fun relatedBoards(boardId: Long, userId: Long?): List<SimpleBoardDTO> {
        return boardService.relatedBoards(boardId, userId)
    }

    fun getReviews(boardId: Long, userId: Long?): List<BoardReviewDTO> {
        return boardService.getReviews(boardId, userId)
    }

    fun getParticipatedGames(userId: Long): List<ParticipatedBoardDTO> {
        return boardService.getParticipatedGames(userId)
    }

    fun getWroteReviews(userId: Long): List<BoardReviewDTO> {
        return boardService.getWroteReviews(userId)
    }

    fun getRecommendReview(userId: Long?): List<BoardReviewDTO> {
        return boardService.getRecommendReview(userId)
    }

    fun deleteBoard(boardId: Long, userId: Long) {
        return boardService.deleteBoard(boardId, userId)
    }

    fun blockBoard(userId: Long, boardId: Long) {
        boardService.blockBoard(userId, boardId)
    }

    fun blockBoardReview(userId: Long, boardId: Long, boardReviewId: Long) {
        boardService.blockBoardReview(userId, boardId, boardReviewId)
    }
//
//    fun excelBoards(dataList: MutableList<ExcelRequestDTO>) {
//        return boardService.excelBoards(dataList.map { it.toCommand() }.toMutableList())
//    }
}
