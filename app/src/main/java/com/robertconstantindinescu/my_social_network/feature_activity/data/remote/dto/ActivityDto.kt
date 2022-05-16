package com.robertconstantindinescu.my_social_network.feature_activity.data.remote.dto

import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.domain.util.ActivityType
import java.text.SimpleDateFormat
import java.util.*


data class ActivityDto(
    val timestamp: Long,
    val userId: String,
    val parentid: String,
    val type:Int,
    val userName: String,
    //activity id
    val id: String
){
    // TODO: take a look at here
    fun toActivity(): Activity{
        return Activity(
            userId = userId,
            parentId = parentid,
            username = userName,
            activityType = when(type){
                ActivityType.FollowedUser.type -> ActivityType.FollowedUser
                ActivityType.LikedPost.type -> ActivityType.LikedPost
                ActivityType.LikedComment.type -> ActivityType.LikedComment
                ActivityType.CommentedOnPost.type -> ActivityType.CommentedOnPost
                else -> ActivityType.FollowedUser
            },
            formattedTime = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).run {
                format(timestamp)
            }

        )
    }
}
