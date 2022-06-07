package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.domain.repository.ProfileRepository
import com.robertconstantindinescu.my_social_network.core.util.Resource
import kotlinx.coroutines.flow.Flow

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String, page: Int): Resource<List<Post>> {
        return repository.getPostsPaged(
            userId = userId,
            page = page
        )
    }
}