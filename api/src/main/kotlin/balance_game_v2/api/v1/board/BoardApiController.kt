package balance_game_v2.api.v1.board

import balance_game_v2.api.v1.board.application.BoardFacade
import balance_game_v2.api.v1.board.http.req.CreateBoardRequestDTO
import balance_game_v2.api.v1.board.http.res.BoardDetailResponseDTO
import balance_game_v2.api.v1.board.http.res.PageBoardResponseDTO
import balance_game_v2.api.v1.user.application.UserFacade
import balance_game_v2.config.BOARD_V2
import balance_game_v2.config.BOARD_V2_PREFIX
import domain.board.model.BoardSortCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = BOARD_V2)
@RestController
@RequestMapping(BOARD_V2_PREFIX)
class BoardApiController (
    val userFacade: UserFacade,
    val boardFacade: BoardFacade,
) {
    @Operation(summary = "[게임-001] 게임 등록")
    @PostMapping("/board")
    fun createBoard(
        @RequestAttribute("email") email: String,
        @RequestBody request: CreateBoardRequestDTO,
    ) {
        val user = userFacade.getUserByEmail(email)

        boardFacade.createBoard(user.userId, request)
    }

    @Operation(summary = "[게임-002] 게임 리스트 조회(페이징)")
    @GetMapping("/boards")
    fun getBoards(
        @RequestAttribute("email") email: String,
        @RequestParam("query") query: String?,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sortCondition") sortCondition: BoardSortCondition?,
    ): PageBoardResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return boardFacade.getBoards(query, page, size, sortCondition)
    }

    @Operation(summary = "[게임-003] 게임 상세 조회")
    @GetMapping("/boards/{boardId}")
    fun getBoardDetail(
        @RequestAttribute("email") email: String,
        @PathVariable("boardId") boardId: Long,
    ): BoardDetailResponseDTO {
        val user = userFacade.getUserByEmail(email)

        return boardFacade.getBoardDetail(boardId)
    }

}