package balance_game_v2.api

import balance_game_v2.api.http.req.ModifyBoardRequestDTO
import balance_game_v2.api.http.req.ModifyReviewRequestDTO
import balance_game_v2.application.BoardBackofficeFacade
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/backoffice/v2/board/api")
class BoardApiController(
    private val boardBackofficeFacade: BoardBackofficeFacade,
) {
    @PutMapping("/boards/{boardId}")
    fun modifyBoard(
        @RequestBody request: ModifyBoardRequestDTO,
        @PathVariable boardId: Long,
    ) {
        boardBackofficeFacade.modifyBoard(boardId, request)
    }

    @DeleteMapping("/boards/{boardId}")
    fun deleteBoard(
        @PathVariable boardId: Long,
    ) {
        boardBackofficeFacade.deleteBoard(boardId)
    }

    @PutMapping("/review")
    fun modifyBoardReview(
        @RequestBody request: ModifyReviewRequestDTO,
    ) {
        boardBackofficeFacade.modifyBoardReview(request)
    }

    @DeleteMapping("/boards/{boardId}/review/{boardReviewId}")
    fun deleteBoardReview(
        @PathVariable boardId: Long,
        @PathVariable boardReviewId: Long,
    ) {
        boardBackofficeFacade.deleteBoardReview(boardId, boardReviewId)
    }
}
