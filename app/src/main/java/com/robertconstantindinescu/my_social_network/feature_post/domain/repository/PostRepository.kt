package com.robertconstantindinescu.my_social_network.feature_post.domain.repository

import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Constants.PAGE_SIZE_POSTS
import com.robertconstantindinescu.my_social_network.core.util.Resource

interface PostRepository {

    suspend fun getPostForFollows(page: Int=0, pageSize: Int = PAGE_SIZE_POSTS): Resource<List<Post>>
}