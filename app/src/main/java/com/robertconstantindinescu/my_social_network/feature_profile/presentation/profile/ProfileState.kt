package com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile

import androidx.paging.LoadState
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false

)
