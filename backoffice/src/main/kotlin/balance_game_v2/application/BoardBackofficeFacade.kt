package balance_game_v2.application

import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.dto.PageBoardDTO
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
}
