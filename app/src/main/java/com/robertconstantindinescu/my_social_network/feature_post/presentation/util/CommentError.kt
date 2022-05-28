package com.robertconstantindinescu.my_social_network.feature_post.presentation.util

import com.robertconstantindinescu.my_social_network.core.util.Error

sealed class CommentError: Error() {
    object FieldEmpty: CommentError()
}
