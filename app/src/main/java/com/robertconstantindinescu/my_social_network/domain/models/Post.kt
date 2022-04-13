package com.robertconstantindinescu.my_social_network.domain.models

data class Post(
    val username: String,
    val imageUrl: String,
    val profilePicture: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int

)
