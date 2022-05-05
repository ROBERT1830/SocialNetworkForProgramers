package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

data class PostUseCases(
    val getPostForFollowsUseCase: GetPostForFollowsUseCase,
    val createPostUseCase:CreatePostUseCae
)
