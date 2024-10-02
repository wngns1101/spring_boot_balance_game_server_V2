package balance_game_v2.controller

import balance_game_v2.domain.admin.AdminService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class IndexController(
    private val adminService: AdminService,
) {
    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/sign-in")
    fun login(
//        @RequestBody request: SignInRequestDTO,
    ): String {
        return "sign-in"
    }
}
