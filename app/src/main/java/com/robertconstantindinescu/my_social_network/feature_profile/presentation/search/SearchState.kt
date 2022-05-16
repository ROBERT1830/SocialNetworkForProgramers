package com.robertconstantindinescu.my_social_network.feature_profile.presentation.search

import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)