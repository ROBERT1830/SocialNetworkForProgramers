package com.robertconstantindinescu.my_social_network.core.presentation

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false //when we scroll to the very button
)
