package com.robertconstantindinescu.my_social_network.feature_auth.domain.models

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource

data class LoginResult(
    val emailError: AuthError?= null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)