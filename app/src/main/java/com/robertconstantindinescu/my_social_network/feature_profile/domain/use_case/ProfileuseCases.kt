package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import com.robertconstantindinescu.my_social_network.core.domain.use_case.ToggleFollowStateForUserUseCase
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.LogoutUseCase

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val getSkillsUseCase: GetSkillsUseCase,
    val setSkillSelectedUseCase: SetSkillSelectedUseCase,
    val getPostsForProfileUseCase: GetPostsForProfileUseCase,
    val searchUserUseCase: SearchUserUseCase,
    val toggleFollowStateForUserUseCase: ToggleFollowStateForUserUseCase,
    val logout: LogoutUseCase
)
