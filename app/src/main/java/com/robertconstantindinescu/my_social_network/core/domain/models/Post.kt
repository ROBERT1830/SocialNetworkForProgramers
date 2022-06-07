package com.robertconstantindinescu.my_social_network.core.domain.models

data class Post(
    //needed to pass in nav arguments
    val id: String,
    val userId:String, //when we clik on the user we want to go to the profile so we need userId,
    //in backend we know user id and then we can get the name
    val username: String,
    //from server
    val imageUrl: String,
    val profilePicture: String,
    val description: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean

)
