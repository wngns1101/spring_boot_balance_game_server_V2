package balance_game_v2.api.v1.board.application

import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.req.toCommand
import balance_game_v2.api.v1.board.http.res.BoardDetailResponseDTO
import balance_game_v2.api.v1.board.http.res.PageBoardResponseDTO
import domain.board.BoardService
import domain.board.dto.BoardDetailDTO
import domain.board.dto.BoardListDTO
import domain.board.dto.PageBoardDTO
import domain.board.model.BoardSortCondition
import org.springframework.stereotype.Component

@Component
class BoardFacade (
    val boardService: BoardService,
){

    fun createBoard(userId: Long, request: CreateBoardRequestDTO) {
        boardService.createBoard(userId, request.toCommand())
    }

    fun getBoards(query: String?, page: Int, size: Int, sortCondition: BoardSortCondition?): PageBoardResponseDTO {
        return PageBoardResponseDTO(boardService.getBoards(query, page, size, sortCondition))
    }

    fun getBoardDetail(boardId: Long): BoardDetailResponseDTO {
        return BoardDetailResponseDTO(boardService.getBoardDetail(boardId))
    }
}
