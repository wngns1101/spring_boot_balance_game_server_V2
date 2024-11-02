package balance_game_v2.controller

import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.model.BoardSortCondition
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/board")
class BoardController(
    private val boardService: BoardService,
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
        val boards = boardService.getBoards(query, adjustedPage, size, sortCondition, null, null)

        model.addAttribute("boards", boards.boards)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", boards.totalPage)

        return "boards"
    }
}