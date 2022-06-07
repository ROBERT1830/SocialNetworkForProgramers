package com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed

/**
 * Whaat the user could do.
 * could laod more post by scrolling
 */
sealed class MainFeedEvent{
//   object LoadMorePosts: MainFeedEvent()
//    object LoadedPage: MainFeedEvent() //stop the loading and load a page
    data class LikedPost(val postId: String): MainFeedEvent()

}


