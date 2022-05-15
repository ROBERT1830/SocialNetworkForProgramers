package com.robertconstantindinescu.my_social_network.feature_profile.data.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response.ProfileResponse
import com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response.SkillDto
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileApi {
    //pass the user id as a parameter. Because, if there is one userId attach means that we
    //will look at that user profile. Else is our own profile
    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?, // could be nullable because a user could choose to not update the maker or profilePicutre
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("/api/skills/get")
    suspend fun getSkills(): List<SkillDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}