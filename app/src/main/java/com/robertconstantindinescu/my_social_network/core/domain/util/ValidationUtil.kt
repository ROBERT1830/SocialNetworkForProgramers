package com.robertconstantindinescu.my_social_network.core.domain.util

import android.util.Patterns
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.AuthError

object ValidationUtil {

    fun validateEmail(email:String): AuthError? {
        val trimmedEmail = email.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return AuthError.InvalidEmail
        }
        /**
         * If we dont enter enything for the email then the first if will trigger
         * but then the second if will be trigger and will be overriden the emailErorr
         * with field empty. So in that way we distinguish between empty and invalid (when is not
         * empty but the email is not correct)
         */
        if (trimmedEmail.isBlank()) {
            //override error and change it to fieldEmpty if is the case.
            return AuthError.FieldEmpty
        }

        return null
    }

    fun validateUsername(username: String): AuthError? {
        val trimmedUsername = username.trim()

        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShor
        }

        if (trimmedUsername.isBlank()) {
            return AuthError.FieldEmpty
        }
        return null
    }

    fun validatePassword(password: String): AuthError? {
        val capitalLettersInPassword = password.any {
            it.isUpperCase()
        }
        val numberInPassword = password.any {
            it.isDigit()
        }

        if (!capitalLettersInPassword || !numberInPassword) {

            return AuthError.InvalidPassword

        }

        if (password.length < Constants.MIN_PASSWORD_LENGTH){
            return AuthError.InputTooShor
        }

        if (password.isBlank()){
            return AuthError.FieldEmpty
        }
        return null
    }
}