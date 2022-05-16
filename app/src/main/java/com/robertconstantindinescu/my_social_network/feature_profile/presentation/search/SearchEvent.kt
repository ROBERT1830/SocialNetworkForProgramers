package com.robertconstantindinescu.my_social_network.feature_profile.presentation.search

sealed class SearchEvent {
    data class Query(val query: String): SearchEvent()
    data class ToggleFollowState(val userId: String): SearchEvent()
}
