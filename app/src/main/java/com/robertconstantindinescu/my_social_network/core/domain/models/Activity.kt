package com.robertconstantindinescu.my_social_network.core.domain.models

import com.robertconstantindinescu.my_social_network.domain.util.ActivityType

data class Activity(
    //we want the userId to navigate to its profile if we click in the name
    val userId: String,
    //the id of the post or comment on which the activity was performed
    val parentId: String,
    //the name of hte user that did some kind of activity
    val username: String,
    val activityType: ActivityType,
    val formattedTime: String
)
