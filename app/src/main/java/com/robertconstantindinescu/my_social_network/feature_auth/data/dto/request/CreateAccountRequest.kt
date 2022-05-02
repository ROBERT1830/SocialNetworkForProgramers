package com.robertconstantindinescu.my_social_network.feature_auth.data.dto.request

data class CreateAccountRequest(
    val email: String,
    val username: String,
    val password: String
)
