package com.robertconstantindinescu.my_social_network.core.domain.states

import com.robertconstantindinescu.my_social_network.core.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error:Error? = null,
    val isPasswordVisible: Boolean = false
)