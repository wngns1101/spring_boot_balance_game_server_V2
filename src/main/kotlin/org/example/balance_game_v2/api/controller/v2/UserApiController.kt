package org.example.balance_game_v2.api.controller.v2

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.balance_game_v2.api.application.UserFacade
import org.example.balance_game_v2.api.controller.v2.req.SignUpRequestDTO
import org.example.balance_game_v2.config.USER_V2
import org.example.balance_game_v2.config.USER_V2_PREFIX
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = USER_V2)
@RestController
@RequestMapping(USER_V2_PREFIX)
class UserApiController(
    private val userFacade: UserFacade
) {
    @Operation(summary = "[유저-001] 회원가입")
    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody signUpRequestDTO: SignUpRequestDTO
    ) {
        val result = userFacade.signUp(signUpRequestDTO.toCommand(signUpRequestDTO))
    }

    @Operation(summary = "[유저-002] 로그인")
    @GetMapping("/login")
    fun login(){

    }
}
