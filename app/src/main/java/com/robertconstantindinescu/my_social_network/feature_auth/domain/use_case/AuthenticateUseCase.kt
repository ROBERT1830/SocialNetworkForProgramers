package com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource{
        return repository.authenticate()
    }
}