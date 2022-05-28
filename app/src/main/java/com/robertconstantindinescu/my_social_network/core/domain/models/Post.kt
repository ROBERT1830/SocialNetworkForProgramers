package com.robertconstantindinescu.my_social_network.core.domain.models

data class Post(
    //needed to pass in nav arguments
    val id: String,
    //in backend we know user id and then we can get the name
    val username: String,
    //from server
    val imageUrl: String,
    val profilePicture: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int

)
