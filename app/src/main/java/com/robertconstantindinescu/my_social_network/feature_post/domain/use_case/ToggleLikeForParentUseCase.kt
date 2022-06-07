package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class ToggleLikeForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        parentId: String,
        parentType: Int,
        isLiked: Boolean
    ): SimpleResource {
        return if(isLiked) {
            repository.unlikeParent(parentId, parentType)
        } else {
            repository.likeParent(parentId, parentType)
        }
    }
}