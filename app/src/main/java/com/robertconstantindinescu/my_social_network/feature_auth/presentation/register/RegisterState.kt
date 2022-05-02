package com.robertconstantindinescu.my_social_network.feature_auth.presentation.register

import com.robertconstantindinescu.my_social_network.core.util.UiText

data class RegisterState(
    val successful: Boolean? = null,
    val message: UiText? = null,
    val isLoading: Boolean = false
)
