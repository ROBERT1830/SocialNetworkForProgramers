package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostForFollowsUseCase(
    private val repository: PostRepository
) {

//    suspend operator fun invoke(page: Int, pageSize: Int): Resource<List<Post>>{
//        return repository.getPostForFollows(page, pageSize)
//    }

    //not a suspend funciton because we return a Flow which is allready a suspend funciton
     operator fun invoke(page: Int = 0, pageSize: Int = Constants.PAGE_SIZE_POSTS): Flow<PagingData<Post>> {
        return repository.posts
    }
}