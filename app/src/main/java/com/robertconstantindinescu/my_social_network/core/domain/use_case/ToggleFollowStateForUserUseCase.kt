package com.robertconstantindinescu.my_social_network.core.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.domain.repository.ProfileRepository

class ToggleFollowStateForUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String, isFollowing: Boolean): SimpleResource{
        return if (isFollowing){
            repository.unfollowUser(userId)
        }else{
            repository.followUser(userId)
        }
    }
}