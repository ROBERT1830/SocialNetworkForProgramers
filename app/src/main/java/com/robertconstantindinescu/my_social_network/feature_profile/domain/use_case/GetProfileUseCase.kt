package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Profile
import com.robertconstantindinescu.my_social_network.core.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Resource<Profile>{
        return repository.getProfile(userId)
    }
}