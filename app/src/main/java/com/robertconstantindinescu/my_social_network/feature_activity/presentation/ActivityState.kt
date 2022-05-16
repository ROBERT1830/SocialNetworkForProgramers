package com.robertconstantindinescu.my_social_network.feature_activity.presentation

import com.robertconstantindinescu.my_social_network.core.domain.models.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false
)
