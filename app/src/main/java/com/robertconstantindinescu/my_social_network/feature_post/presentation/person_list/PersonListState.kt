package com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list


import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
