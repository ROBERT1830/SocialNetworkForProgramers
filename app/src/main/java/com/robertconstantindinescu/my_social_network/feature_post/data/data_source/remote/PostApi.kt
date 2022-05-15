package com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import robertconstantin.example.data.requests.CreatePostRequest

interface PostApi {
    @GET("api/post/get")
    suspend fun getPostForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>


    /**
     * This request will be a multipart because we want to send either JSON and file with an image
     * And in this request we have 2 parts. the body which represents the json with the
     * description and the file for the image.
     */
    @Multipart
    @POST("api/post/create")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<Unit>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}