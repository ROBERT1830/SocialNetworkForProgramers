package com.robertconstantindinescu.my_social_network.feature_auth.data.repository

import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.data.dto.response.BasicApiResponse
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_auth.data.dto.request.CreateAccountRequest
import com.robertconstantindinescu.my_social_network.feature_auth.data.remote.AuthApi
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi
): AuthRepository {

    override suspend fun register(
        email:String,
        username:String,
        password:String
    ): SimpleResource {

        val request = CreateAccountRequest(email, username, password)

         return try {
            val response = api.register(request = request)
            if (response.successful){
                Resource.Success(Unit) //no data
            }else{
                //make a check for null message. I know that from server we sent allwas a message but
                //could happen that is not attatched for some reason
                    response.message?.let { msg ->
                        Resource.Error(UiText.DynamicString(msg))
                    }?: Resource.Error(UiText.StringResource(R.string.error_unknown))

            }

             //couldnt send the data or coudl not receive. Some kind of time out
        }catch (e: IOException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )


            //exception for verything that doesnt start with the 2 xx (200 ok)
        }catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }


    }
}