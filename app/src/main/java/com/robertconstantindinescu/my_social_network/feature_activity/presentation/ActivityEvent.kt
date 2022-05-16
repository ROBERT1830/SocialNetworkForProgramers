package com.robertconstantindinescu.my_social_network.feature_activity.presentation

sealed class ActivityEvent{
    data class ClickedOnUser(val userId: String): ActivityEvent()
    data class ClickedOnParent(val parentId: String): ActivityEvent()
}
