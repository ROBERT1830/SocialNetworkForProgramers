package com.robertconstantindinescu.my_social_network.core.domain.models

data class User(
    val profilePicture: String,
    val username: String,
    val description:String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount: Int
)
