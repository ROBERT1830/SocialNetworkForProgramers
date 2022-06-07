package com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote

import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.core.data.dto.response.UserItemDto
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.dto.CommentDto
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.request.CreateCommentRequest
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.request.LikeUpdateRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {
    @GET("api/post/get")
    suspend fun getPostForFollows(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>

    @GET("/api/user/posts")
    suspend fun getPostForProfile(
        @Query("userId") userId: String,
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


    @GET("/api/post/details")
    suspend fun getPostDetails(
        @Query("postId") postId: String
    ): BasicApiResponse<Post>

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): List<CommentDto>

    @POST
    suspend fun createComment(
        @Body request: CreateCommentRequest
    ): BasicApiResponse<Unit>

    @POST("/api/like")
    suspend fun likeParent(
        @Body request: LikeUpdateRequest
    ): BasicApiResponse<Unit>

    //to delete we need queies. to delete we need a parentId and a parentType
    @DELETE("/api/unlike")
    suspend fun unlikeParent(
        @Query("parentId") parentId: String,
        @Query("parentType") parentType: Int
    ): BasicApiResponse<Unit>

    @GET("/api/like/parent")
    suspend fun getLikesForParent(
        @Query("parentId") parentId: String
    ): List<UserItemDto>

    @DELETE("/api/post/delete")
    suspend fun deletePost(
        @Query("postId") postId: String,
    )

    companion object {
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}