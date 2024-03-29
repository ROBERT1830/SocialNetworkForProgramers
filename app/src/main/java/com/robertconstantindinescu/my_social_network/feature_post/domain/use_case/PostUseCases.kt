package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

data class PostUseCases(
    val getPostForFollowsUseCase: GetPostForFollowsUseCase,
    val createPostUseCase:CreatePostUseCae,
    val getPostDetailsUseCase: GetPostDetailsUseCase,
    val getCommentsForPostUseCase: GetCommentsForPostUseCase,
    val createCommentUseCase: CreateCommentUseCase,
    val toggleLikeForParentUseCase: ToggleLikeForParentUseCase,
    val getLikesForParentUseCase: GetLikesForParentUseCase
)
