package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import android.net.Uri
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill

sealed class EditProfileEvent{
    data class EnteredUserName(val value:String): EditProfileEvent()
    data class EnteredGitHubUrl(val value:String): EditProfileEvent()
    data class EnteredInstagramUrl(val value:String): EditProfileEvent()
    data class EnteredLinkedIntUrl(val value:String): EditProfileEvent()
    data class EnteredBio(val value:String): EditProfileEvent()

    data class CropProfileImage(val uri: Uri?):EditProfileEvent()
    data class CropBannerImage(val uri: Uri?):EditProfileEvent()
    data class SetSkillSelected(val skill: Skill):EditProfileEvent()

    object UpdateProfile: EditProfileEvent()
}
