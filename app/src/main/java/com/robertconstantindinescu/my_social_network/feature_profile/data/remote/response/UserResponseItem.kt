package com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response

import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.UserItem

data class UserResponseItem(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio:String,
    val isFollowing: Boolean
){
    fun toUserItem():UserItem{
        return UserItem(
            userId = userId,
            userName = userName,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            isFollowing = isFollowing


        )
    }
}
