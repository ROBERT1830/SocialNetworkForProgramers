package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class CreatePostUseCae(
    private val repository: PostRepository
) {
    suspend operator fun invoke(description: String, imageUri: Uri): SimpleResource{
        return repository.createPost(description, imageUri)
    }
}