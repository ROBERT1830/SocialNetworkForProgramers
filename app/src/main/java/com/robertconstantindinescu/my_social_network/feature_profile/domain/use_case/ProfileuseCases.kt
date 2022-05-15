package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val getSkillsUseCase: GetSkillsUseCase,
    val setSkillSelectedUseCase: SetSkillSelectedUseCase
)
