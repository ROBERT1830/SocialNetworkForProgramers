package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import com.robertconstantindinescu.my_social_network.core.domain.models.UserItem
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.feature_profile.domain.repository.ProfileRepository

class SearchUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(query:String): Resource<List<UserItem>>{
        //when the query is blanck we dont want to make a request.
        if (query.isBlank()){
            return Resource.Success(data = emptyList())
        }
        return repository.searchUser(query)
    }
}