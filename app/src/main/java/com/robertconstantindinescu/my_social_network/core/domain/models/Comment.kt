package com.robertconstantindinescu.my_social_network.core.domain.models

data class Comment(
    val commentId: String ,
    val username: String ,
    val profilePictureUrl: String ,
    val formattedTime: String ,
    val comment: String ,
    val isLiked: Boolean ,
    val likeCount: Int
)
