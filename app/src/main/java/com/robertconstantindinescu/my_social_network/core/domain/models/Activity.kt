package com.robertconstantindinescu.my_social_network.core.domain.models

import com.robertconstantindinescu.my_social_network.domain.util.ActivityAction

data class Activity(
    val username: String,
    val actionType: ActivityAction,
    val formattedTime: String
)
