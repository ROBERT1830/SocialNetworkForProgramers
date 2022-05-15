package com.robertconstantindinescu.my_social_network.feature_profile.domain.repository

import android.net.Uri
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Profile
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.UpdateProfileData

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    //because here we are making a post reqeust, the return type will be
    //basicApi with suscces true to indicate that the update ahs been resolved.
    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills():Resource<List<Skill>>
}