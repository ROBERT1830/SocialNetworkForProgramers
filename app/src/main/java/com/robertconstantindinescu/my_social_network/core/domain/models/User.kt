package com.robertconstantindinescu.my_social_network.core.domain.models

data class User(
    val userId: String, //this is important to get the userId when click in one user to see its profile.
    val profilePictureUrl: String,
    val username: String,
    val bio:String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount: Int
)
