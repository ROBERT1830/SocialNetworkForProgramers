package com.robertconstantindinescu.my_social_network.feature_profile.presentation.util

sealed class EditProfileError: Error(){
    object FieldEmpty: EditProfileError()
}
