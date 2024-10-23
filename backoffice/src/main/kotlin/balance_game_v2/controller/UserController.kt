package balance_game_v2.controller

import balance_game_v2.domain.user.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/users")
    fun getUserPage(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        val adjustedPage = page - 1

        val userPage = userService.getUserPage(query, adjustedPage, size)

        model.addAttribute("users", userPage.users)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", userPage.totalPage)

        return "users"
    }
}
