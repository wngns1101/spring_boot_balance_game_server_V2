package org.example.balance_game_v2.domain.user

import org.example.balance_game_v2.domain.user.repository.AuthRepository

class AuthService (
    private val authRepository: AuthRepository
){
    fun signUp(email: String, password: String){
        if(authRepository.existsByEmail(email)) throw
        authRepository.save()
    }
}