package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import com.robertconstantindinescu.my_social_network.feature_profile.domain.repository.ProfileRepository

class GetSkillsUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(): Resource<List<Skill>>{
        return repository.getSkills()
    }
}