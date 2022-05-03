package com.robertconstantindinescu.my_social_network.feature_auth.domain.repository

import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.feature_auth.data.dto.request.CreateAccountRequest

interface AuthRepository {
    /**
     * Here we can thig that we have to return an BasicApiResponse but no. We dont want to expose
     * that model. Because the user case in the end shouldn't know where the repository get the data from
     * (local, remote) and the Basicapi class exposes that will get the infro from api.
     *
     * So we have to do a maper.
     */
    //here we dont return any kind of data object or so. so put unit.
    // hERE WE ONLY WANT to distinguish between succes true or false. When have an erro we have a message tha tis attatches
    //in the sealed class.

    /**
     * !!!!!--> If in the repo we will user the model. The repo exposes to use cases that
     * is a request to an api. So we force the use case to pass the model. And this is not what we want
     * so intead just send the data needed to register like email pass etc...
     * So in the repo is where the model of CreateAccountRequest will be create from the data
     * the user case passes
     */
    suspend fun register(
        email: String,
        username: String,
        password:String
    ): SimpleResource //is Resource<T> a seled class

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource
    /**
     * The token is gona be stored in shared pref and will be attatched in each and every request
     * So will be placed in the data layer.We have to think that if will leave the data layer or not
     */

    suspend fun authenticate(): SimpleResource
}