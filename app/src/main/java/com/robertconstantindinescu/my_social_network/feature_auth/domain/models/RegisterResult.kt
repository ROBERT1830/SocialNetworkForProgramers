package com.robertconstantindinescu.my_social_network.feature_auth.domain.models

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource


data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource ? = null //is null because if we have validadte errors we dont have resource (sealed class)
)
