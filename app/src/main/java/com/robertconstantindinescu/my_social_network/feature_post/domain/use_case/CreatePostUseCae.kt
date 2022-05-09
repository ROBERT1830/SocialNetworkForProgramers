package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import android.net.Uri
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class CreatePostUseCae(
    private val repository: PostRepository
) {
    suspend operator fun invoke(description: String, imageUri: Uri?): SimpleResource{
        if (imageUri == null){
            return Resource.Error(uiText = UiText.StringResource(R.string.error_no_image_picked))

        }
        if (description.isBlank()){
            return Resource.Error(uiText = UiText.StringResource(R.string.error_description_blank))
        }
        return repository.createPost(description, imageUri)
    }
}