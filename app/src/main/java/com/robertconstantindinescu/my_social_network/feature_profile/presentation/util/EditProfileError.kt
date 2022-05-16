package com.robertconstantindinescu.my_social_network.feature_profile.presentation.util

import com.robertconstantindinescu.my_social_network.core.util.Error

sealed class EditProfileError: Error(){
    object FieldEmpty: EditProfileError()
}
