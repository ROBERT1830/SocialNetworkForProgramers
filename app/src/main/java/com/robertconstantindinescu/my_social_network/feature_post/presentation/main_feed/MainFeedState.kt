package com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed

import com.robertconstantindinescu.my_social_network.core.domain.models.Post

/**
 * TAhink about what could be changed in the ui
 */
data class MainFeedState(
    //val posts: List<Post> = emptyList(), --> WE HAVE THEM IM THE PAGIN LIBRARY
    val isLoadingFirstTime: Boolean = true, //this is for when the loading takes place for te firs time. Is true initially because we are loading for the first time
    val isLoadingNewPosts: Boolean = false, //this loading will be shown in the bottom for when ask for new data to the server
    //the page while scrolling but not the pageSize because is a constant
    //val page: Int = 0
)
