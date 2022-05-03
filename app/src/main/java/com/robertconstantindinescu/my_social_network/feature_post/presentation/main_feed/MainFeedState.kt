package com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed

import com.robertconstantindinescu.my_social_network.core.domain.models.Post

/**
 * TAhink about what could be changed in the ui
 */
data class MainFeedState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    //the page while scrolling but not the pageSize because is a constant
    val page: Int = 0
)
