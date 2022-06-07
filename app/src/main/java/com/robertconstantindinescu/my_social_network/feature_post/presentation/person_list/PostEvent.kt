package com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list


import com.robertconstantindinescu.my_social_network.core.util.Event

sealed class PostEvent : Event() {
    object OnLiked: PostEvent()
}