package com.robertconstantindinescu.my_social_network.core.domain.models

data class UserItem(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio:String,
    val isFollowing: Boolean
)
