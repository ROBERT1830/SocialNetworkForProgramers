package com.robertconstantindinescu.my_social_network.core.domain.states

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)
