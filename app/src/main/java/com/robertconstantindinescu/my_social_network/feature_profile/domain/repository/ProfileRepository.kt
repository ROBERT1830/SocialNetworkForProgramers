package com.robertconstantindinescu.my_social_network.feature_profile.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.data.dto.response.UserItemDto
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Profile
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.UpdateProfileData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProfileRepository {

    //val posts: Flow<PagingData<Post>>

    fun getPostsPaged(userId: String): Flow<PagingData<Post>>

    suspend fun getProfile(userId: String): Resource<Profile>

    //suspend fun getPostForProfile(page: Int, pageSize: Int): Response<List<Post>>

    //because here we are making a post reqeust, the return type will be
    //basicApi with suscces true to indicate that the update ahs been resolved.
    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills():Resource<List<Skill>>

    suspend fun searchUser(query: String): Resource<List<UserItem>>

    suspend fun followUser(userId:String): SimpleResource

    suspend fun unfollowUser(userId: String): SimpleResource
}