package com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list

import androidx.compose.animation.SizeTransform

sealed class PersonListEvent {
    data class ToggleFollowStateForUser(val userId: String): PersonListEvent()
}
