package com.robertconstantindinescu.my_social_network.feature_post.data.repository

import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_auth.data.dto.request.CreateAccountRequest
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.PostApi
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi
): PostRepository {

    override suspend fun getPostForFollows(page: Int, pageSize: Int): Resource<List<Post>> {

        return try {
            val posts = api.getPostForFollows(page, pageSize)
            Resource.Success(posts)
            //couldnt send the data or coudl not receive. Some kind of time out
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )


            //exception for verything that doesnt start with the 2 xx (200 ok)
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}