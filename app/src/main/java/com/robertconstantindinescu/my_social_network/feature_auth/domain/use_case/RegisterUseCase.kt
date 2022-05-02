package com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
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
        email:String,
        username: String,
        password:String
    ): SimpleResource {

        return repository.register(email.trim(), username.trim(), password.trim())
    }
}