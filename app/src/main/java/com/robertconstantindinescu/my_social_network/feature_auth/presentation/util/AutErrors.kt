package com.robertconstantindinescu.my_social_network.feature_auth.domain.models

import com.robertconstantindinescu.my_social_network.core.util.Error
import com.robertconstantindinescu.my_social_network.feature_auth.presentation.register.RegisterState

sealed class AuthError: Error(){
    object FieldEmpty : AuthError()
    object InputTooShor : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}
