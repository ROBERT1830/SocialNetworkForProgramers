package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import com.robertconstantindinescu.my_social_network.core.domain.models.Comment
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): Resource<List<Comment>>{
        return repository.getCommentsForPost(postId)
    }
}