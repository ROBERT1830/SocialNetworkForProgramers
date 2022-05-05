package com.robertconstantindinescu.my_social_network.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Constants.PAGE_SIZE_POSTS
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    //suspend fun getPostForFollows(page: Int=0, pageSize: Int = PAGE_SIZE_POSTS): Resource<List<Post>>
    val posts: Flow<PagingData<Post>>

    /**
     * The file will be a uri and then we get the filen from that.
     *
     */
    suspend fun createPost(description: String, imageUri: Uri): SimpleResource
}