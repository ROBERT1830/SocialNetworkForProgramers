package com.robertconstantindinescu.my_social_network.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Comment
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    //suspend fun getPostForFollows(page: Int=0, pageSize: Int = PAGE_SIZE_POSTS): Resource<List<Post>>
    //val posts: Flow<PagingData<Post>>
    suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>>

    /**
     * The file will be a uri and then we get the filen from that.
     *
     */
    suspend fun createPost(description: String, imageUri: Uri): SimpleResource

    suspend fun getPostDetails(postId: String): Resource<Post>  //we have some data to attach so is not SimpleResource

    suspend fun getCommentsForPost(postId: String): Resource<List<Comment>>

    suspend fun createComment(postId:String, comment: String): SimpleResource

    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource

    suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>> //returns the users that likes a post

    suspend fun deletePost(postId: String): SimpleResource
}