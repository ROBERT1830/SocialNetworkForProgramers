package com.robertconstantindinescu.my_social_network.feature_post.presentation.post_detail

import com.robertconstantindinescu.my_social_network.core.domain.models.Comment
import com.robertconstantindinescu.my_social_network.core.domain.models.Post

data class PostDetailState(
    //the post object could be changed
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoadingPost: Boolean = false,
    val isLoadingComments: Boolean = false


)
