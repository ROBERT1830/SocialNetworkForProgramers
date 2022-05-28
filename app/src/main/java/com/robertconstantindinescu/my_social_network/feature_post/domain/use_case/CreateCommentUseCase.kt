package com.robertconstantindinescu.my_social_network.feature_post.domain.use_case

import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository

class CreateCommentUseCase(
    val repository: PostRepository
) {

    suspend operator fun invoke(postId: String, comment:String): SimpleResource{
        if (comment.isBlank()){
            return Resource.Error(UiText.StringResource(R.string.error_field_empty))
        }
        if (postId.isBlank()){
            return Resource.Error(UiText.unknownError())
        }
        return repository.createComment(postId, comment)
    }
}