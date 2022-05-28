package com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail

/**
 * Things that user can do
 */
sealed class PostDetailEvent{
    object LikePost: PostDetailEvent()
    object Comment: PostDetailEvent()
    data class LikeComment(val commentId: String): PostDetailEvent()
    object SharedPost: PostDetailEvent()
    data class EnteredComment(val comment: String): PostDetailEvent()
}
