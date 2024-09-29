package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.Board
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardQueryDslRepository {
    fun search(query: String?, pageable: Pageable, sortCondition: BoardSortCondition?, themeId: Long?, boardReportsIds: List<Long>?): Page<Board>
    fun todayRecommendGame(): List<Board>
    fun relatedBoards(boardId: Long, themeId: Long, boardReportsIds: List<Long>?): List<Board>
    fun todayRecommendGameByUserId(myBoardIds: List<Long>?, boardReportIds: List<Long>?): Board
}
