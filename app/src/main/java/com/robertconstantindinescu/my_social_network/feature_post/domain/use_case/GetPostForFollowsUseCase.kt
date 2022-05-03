package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(page: Int, pageSize: Int): Resource<List<Post>>{
        return repository.getPostForFollows(page, pageSize)
    }
}