package org.example.balance_game_v2.domain.user

import org.example.balance_game_v2.domain.error.AlreadyExistEmailException
import org.example.balance_game_v2.domain.user.entity.Auth
import org.example.balance_game_v2.domain.user.repository.AuthRepository
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val authRepository: AuthRepository
){
    fun signUp(email: String, password: String): String{
        if(authRepository.existsByEmail(email)) throw AlreadyExistEmailException()

        val auth = Auth(
            email = email,
            password = password,
        )

        authRepository.save(auth)

        return email
    }
}