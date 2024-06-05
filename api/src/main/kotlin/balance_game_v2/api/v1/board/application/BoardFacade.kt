package balance_game_v2.api.v1.board.application

import balance_game_v2.api.v1.board.http.req.*
import balance_game_v2.api.v1.board.http.res.*
import domain.board.BoardService
import domain.board.dto.*
import domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardFacade (
    val boardService: BoardService,
){

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

}
