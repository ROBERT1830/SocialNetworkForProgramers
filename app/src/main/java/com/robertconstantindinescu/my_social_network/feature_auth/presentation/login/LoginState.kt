package com.robertconstantindinescu.my_social_network.feature_auth.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false
)
