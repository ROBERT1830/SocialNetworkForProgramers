package com.robertconstantindinescu.my_social_network.core.util


import com.robertconstantindinescu.my_social_network.core.domain.models.Post

interface PostLiker {

    suspend fun toggleLike(
        posts: List<Post>,
        parentId: String,
        //function to make the request
        onRequest: suspend (isLiked: Boolean) -> SimpleResource,
        //current post list
        onStateUpdated: (List<Post>) -> Unit
    )
}