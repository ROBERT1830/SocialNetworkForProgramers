package com.robertconstantindinescu.my_social_network.core.data.dto.response

import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem

data class UserItemDto(
    val userId: String,
    val userName: String,
    val profilePictureUrl: String,
    val bio:String,
    val isFollowing: Boolean
){
    fun toUserItem(): UserItem {
        return UserItem(
            userId = userId,
            userName = userName,
            profilePictureUrl = profilePictureUrl,
            bio = bio,
            isFollowing = isFollowing


        )
    }
}
