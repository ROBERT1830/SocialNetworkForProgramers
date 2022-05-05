package com.robertconstantindinescu.my_social_network.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Constants.SHARED_PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * If we provide the token here as a singleton, will not change through the whole time life
     * app
     */
    @Provides
    @Singleton
    fun provideJwtToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString(Constants.KEY_JWT_TOKEN, "")?: ""
    }

    /**
     * Here we add an interceptor that will take the jwt token which can be get from shared preferences
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor{
                /**
                 * Here we are telling that the okhttp client will have an
                 * interceptor in each and every request to add
                 * a header for that reqeust.
                 * Then proceed with the request but with the modified one which is
                 * the body + header we added
                 */
                val modifiedRequest = it.request().newBuilder()
                        //write the name of the header and its value
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                it.proceed(modifiedRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            SHARED_PREF_NAME,
            MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    
}