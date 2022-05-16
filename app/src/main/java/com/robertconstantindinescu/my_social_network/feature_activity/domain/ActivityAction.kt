package com.robertconstantindinescu.my_social_network.domain.util

//this is a general ActivityAction with different types of actions
sealed class ActivityType(val type: Int){
    object LikedPost: ActivityType(0)
    object LikedComment: ActivityType(1)
    object CommentedOnPost: ActivityType(2)
    object FollowedUser: ActivityType(3)

}