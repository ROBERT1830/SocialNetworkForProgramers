package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import android.net.Uri
import android.util.Patterns
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.UpdateProfileData
import com.robertconstantindinescu.my_social_network.feature_profile.domain.repository.ProfileRepository
import com.robertconstantindinescu.my_social_network.feature_profile.domain.util.ProfileConstants

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(
        updateProfileData: UpdateProfileData,
        profilePictureUri: Uri?,
        bannerUri: Uri?
    ): SimpleResource{

        if (updateProfileData.userName.isBlank()){
            return Resource.Error(
                UiText.StringResource(R.string.error_usename_empty)
            )
        }
        val isValidGitHubUrl = ProfileConstants.GITHUB_PROFILE_REGEX.matches(updateProfileData.githubUrl)
        if (!isValidGitHubUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_github_url)
            )
        }
        val isValidInstagramUrl = ProfileConstants.INSTAGRAM_PROFILE_REGEX.matches(updateProfileData.instagramUrl)
        if (!isValidInstagramUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_instagram_url)
            )
        }
        val isValidLinkedInUrl = ProfileConstants.LINKED_IN_PROFILE_REGEX.matches(updateProfileData.linkedInUrl)
        if (!isValidLinkedInUrl){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_invalid_linkedin_url)
            )
        }
        return repository.updateProfile(
            updateProfileData = updateProfileData,
            profilePictureUri = profilePictureUri,
            bannerImageUri = bannerUri
        )
    }
}