package com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.request

data class LikeUpdateRequest(
    val parentId: String,
    val parentType: Int
)
