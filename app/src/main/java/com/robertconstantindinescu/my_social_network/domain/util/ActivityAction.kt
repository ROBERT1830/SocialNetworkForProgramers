package com.robertconstantindinescu.my_social_network.domain.util

//this is a general ActivityAction with different types of actions
sealed class ActivityAction{

    object LikedPost: ActivityAction()
    object CommentedOnPost: ActivityAction()
    object FollowedYou: ActivityAction()


}
