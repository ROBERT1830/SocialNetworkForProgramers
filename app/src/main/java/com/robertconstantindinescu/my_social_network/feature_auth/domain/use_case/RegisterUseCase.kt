package com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case

import android.util.Patterns
import com.robertconstantindinescu.my_social_network.core.domain.util.ValidationUtil
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.AuthError
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.RegisterResult
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository

/**
 * A use case should not know where the data is going to and from where the data is comming
 * from. if the name contains Request like passing CreateAccountReqeust is clear that will
 * go to an api.
 *
 * For example if you have a note app and you want to update a note. The use case will
 * be on charge to update the note but he doenst know where the note is
 * updated like local or api.
 */
class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): RegisterResult {

        val emailError = ValidationUtil.validateEmail(email)
        val usernameError = ValidationUtil.validateUsername(username)
        val passwordError = ValidationUtil.validatePassword(password)

        //if we have at least 1 error
        if (emailError != null || usernameError != null || passwordError != null){
            //return the errors but not the result
            return RegisterResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,

            )
        }

        val result =  repository.register(email.trim(), username.trim(), password.trim())
        //if there is no error then return only there sult
        return  RegisterResult(
            result = result
        )
    }
}