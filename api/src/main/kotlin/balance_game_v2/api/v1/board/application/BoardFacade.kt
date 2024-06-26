package balance_game_v2.api.v1.board.application

import balance_game_v2.api.v1.board.http.req.BoardModifyRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.req.DeleteBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.req.ModifyBoardCommentRequestDTO
import balance_game_v2.api.v1.board.http.req.toCommand
import domain.board.BoardService
import domain.board.dto.BoardContentDTO
import domain.board.dto.BoardDetailDTO
import domain.board.dto.BoardResultDTO
import domain.board.dto.PageBoardDTO
import domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardFacade(
    val boardService: BoardService,
) {

    fun createBoard(userId: Long, request: CreateBoardRequestDTO) {
        boardService.createBoard(userId, request.toCommand())
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?): PageBoardDTO {
        return boardService.getBoards(query, page, size, sortCondition)
    }

    fun getBoardDetail(boardId: Long): BoardDetailDTO {
        return boardService.getBoardDetail(boardId)
    }

    fun boardHeart(boardId: Long, userId: Long): Boolean {
        return boardService.boardHeart(boardId, userId)
    }

    fun getBoardContents(boardId: Long): List<BoardContentDTO> {
        return boardService.getBoardContents(boardId)
    }

    fun createBoardResult(boardId: Long, boardContentId: Long, userId: Long) {
        boardService.createBoardResult(boardId, boardContentId, userId)
    }

    fun getBoardResult(boardId: Long): List<BoardResultDTO> {
        return boardService.getBoardResult(boardId)
    }

    fun modifyBoard(boardId: Long, request: BoardModifyRequestDTO) {
        return boardService.modifyBoard(boardId, request.toCommand())
    }

    fun createBoardComment(boardId: Long, userId: Long, request: CreateBoardCommentRequestDTO) {
        return boardService.createBoardComment(boardId, userId, request.toCommand())
    }

    fun modifyBoardComment(boardId: Long, userId: Long, request: ModifyBoardCommentRequestDTO) {
        return boardService.modifyBoardComment(boardId, userId, request.toCommand())
    }

    fun deleteBoardComment(boardId: Long, userId: Long, request: DeleteBoardCommentRequestDTO) {
        return boardService.deleteBoardComment(boardId, userId, request.toCommand())
    }

    fun createBoardReport(boardId: Long, userId: Long) {
        boardService.createBoardReport(boardId, userId)
    }

    fun createBoardCommentReport(boardCommentId: Long, userId: Long) {
        boardService.createBoardCommentReport(boardCommentId, userId)
    }
}
