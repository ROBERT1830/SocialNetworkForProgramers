package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case


import com.robertconstantindinescu.my_social_network.core.domain.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {

    operator fun invoke() {
        repository.logout()
    }
}