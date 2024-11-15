package balance_game_v2.controller

import balance_game_v2.application.BoardBackofficeFacade
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/board")
class BoardController(
    private val boardBackofficeFacade: BoardBackofficeFacade,
) {
    @GetMapping("/boards")
    fun boards(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sortCondition") sortCondition: BoardSortCondition?,
        model: Model
    ): String {
        val adjustedPage = page - 1
        val boards = boardBackofficeFacade.getBoards(query, adjustedPage, size, sortCondition, null, null)

        model.addAttribute("boards", boards.boards)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", boards.totalPage)

        return "boards"
    }

    @GetMapping("/boards/{boardId}")
    fun getBoardDetail(
        @PathVariable("boardId") boardId: Long,
    ): String {
        val board = boardBackofficeFacade.getBoardDetail(boardId)

        return "board-detail"
    }

    @GetMapping("/reviews")
    fun reviews(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        val adjustedPage = page - 1
        val boardReview = boardBackofficeFacade.getReviews(query, adjustedPage, size)

        model.addAttribute("boardReviews", boardReview.boardReviews)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", boardReview.totalPage)

        return "board-reviews"
    }

    @GetMapping("/reviews/{reviewId}")
    fun getReviewDetail(
        @PathVariable("reviewId") boardReviewId: Long,
        model: Model,
    ): String {
        val boardReview = boardBackofficeFacade.getBoardReviewDetail(boardReviewId)

        model.addAttribute("boardReview", boardReview)

        return "board-review-detail"
    }
}
