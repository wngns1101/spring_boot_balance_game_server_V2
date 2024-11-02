package balance_game_v2.api

import balance_game_v2.api.application.UserFacade
import balance_game_v2.api.http.req.ModifyUserRequestDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/backoffice/v2/user/api")
class UserApiController(
    private val userFacade: UserFacade,
) {
    @PostMapping("/upload-profile")
    fun postProfile(
        @RequestParam(value = "profile") file: MultipartFile,
    ): String {
        return userFacade.postProfileByAdmin(file)
    }

    @PutMapping("/user")
    fun modifyUser(
        @RequestBody request: ModifyUserRequestDTO,
    ) {
        userFacade.modifyUserByAdmin(request)
    }
}