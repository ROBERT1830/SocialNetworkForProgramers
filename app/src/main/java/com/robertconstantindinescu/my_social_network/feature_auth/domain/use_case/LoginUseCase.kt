package com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case

import com.robertconstantindinescu.my_social_network.core.domain.util.ValidationUtil
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.AuthError
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.LoginResult
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.RegisterResult
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    /**
     * Validadte only if the fields are empty to not fire off a request with empty body
     */
    suspend operator fun invoke(email:String, password: String): LoginResult {
        val emailError = if (email.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(password.isBlank()) AuthError.FieldEmpty else null

        if (emailError != null || passwordError != null){
            return LoginResult(emailError = emailError, passwordError = passwordError)
        }

        return LoginResult(
            result = repository.login(email, password)
        )
    }
}