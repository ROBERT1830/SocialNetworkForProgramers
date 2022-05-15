package com.robertconstantindinescu.my_social_network.feature_auth.data.repository

import android.content.SharedPreferences
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Constants.KEY_JWT_TOKEN
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.dto.request.CreateAccountRequest
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.dto.request.LoginRequest
import com.robertconstantindinescu.my_social_network.feature_auth.data.data_source.remote.AuthApi
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences //to save the token. We use shared preferences and not data store because in shared we can encrypt the content. woth data store is not posible yet.
) : AuthRepository {

    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource {

        val request = CreateAccountRequest(email, username, password)

        return try {
            val response = api.register(request = request)
            if (response.successful) {
                Resource.Success(Unit) //no data
            } else {
                //make a check for null message. I know that from server we sent allwas a message but
                //could happen that is not attatched for some reason
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))

            }

            //couldnt send the data or coudl not receive. Some kind of time out
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )


            //exception for verything that doesnt start with the 2 xx (200 ok)
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun login(email: String, password: String): SimpleResource {
        val request = LoginRequest(email, password)

        return try {
            val response = api.login(request = request)
            if (response.successful) {
                //if the response is susccesful then save the token.
                response.data?.let { authResponse ->
                    sharedPreferences.edit()
                        .putString(KEY_JWT_TOKEN, authResponse.token)
                        .putString(Constants.KEY_USER_ID, authResponse.userId)
                        .apply()
                }
                Resource.Success(Unit) //no data
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    /**
     * This funcion is fire of in th splash screen.
     * In that screen we authenticate and depending on th eresult of this funtion
     * we forward the user to the login screen or directly to the main feed screen.
     *
     * in all eror cases we want to show the login.
     */
    override suspend fun authenticate(): SimpleResource {

        return try {
            api.authenticate()
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }

        /**
         * In the case there is an unauthorized error we will show the login screen because the
         * token as expired. And the user just need to login again.
         *
         * But in ither cases, that are an error like we cant reach the server what can we do then?
         */
    }
}







































