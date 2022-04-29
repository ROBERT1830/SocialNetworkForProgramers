package com.robertconstantindinescu.my_social_network.feature_post.presentation.util

sealed class PostDescriptionError: Error(){
    object FieldEmpty: PostDescriptionError()
}