package com.robertconstantindinescu.my_social_network.core.presentation.util

import com.robertconstantindinescu.my_social_network.core.util.UiText

sealed class UiEvent {
    data class ShowSnackBar(val uiText: UiText): UiEvent()
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
}