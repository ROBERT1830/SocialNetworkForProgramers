package com.robertconstantindinescu.my_social_network.feature_auth.data.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.feature_auth.data.dto.request.CreateAccountRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/user/create")
    suspend fun register(
        //@Body ---> The object will be serialized using the Retrofit instance Converter and the
        // result will be set directly as the request body
        @Body request: CreateAccountRequest
    ): BasicApiResponse


    /**
     * We put the base url here and not in a global constants file because that maybe at some
     * point we could decide that we take the authentication part of our api and put this
     * in a different backend server.Maybe an authentication server. And then we have the base url
     * for every single part of our api.
     */
    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}