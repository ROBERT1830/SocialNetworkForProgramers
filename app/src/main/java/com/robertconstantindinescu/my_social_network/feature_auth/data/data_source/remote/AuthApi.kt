package com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.dto.request.CreateAccountRequest
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.dto.request.LoginRequest
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.dto.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("api/user/create")
    suspend fun register(
        //@Body ---> The object will be serialized using the Retrofit instance Converter and the
        // result will be set directly as the request body
        @Body request: CreateAccountRequest
    ): BasicApiResponse<Unit>


    /**
     * Here when we create the token and then we send it back from server. So the
     * return type must be AuthResponse.
     * But hte thing is that when the checking password failed a BasciApirESPONSE IS SEND
     * So we need a way to parse both
     */
    @POST("api/user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): BasicApiResponse<AuthResponse>

    @GET("api/user/authenticate")
    suspend fun authenticate() //Not return anything

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