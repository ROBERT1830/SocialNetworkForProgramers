package com.robertconstantindinescu.my_social_network.feature_profile.data.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {
    //pass the user id as a parameter. Because, if there is one userId attach means that we
    //will look at that user profile. Else is our own profile
    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}