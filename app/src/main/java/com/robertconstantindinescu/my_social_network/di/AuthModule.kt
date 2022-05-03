package com.robertconstantindinescu.my_social_network.di

import android.content.SharedPreferences
import com.robertconstantindinescu.my_social_network.feature_auth.data.remote.AuthApi
import com.robertconstantindinescu.my_social_network.feature_auth.data.remote.AuthApi.Companion.BASE_URL
import com.robertconstantindinescu.my_social_network.feature_auth.data.repository.AuthRepositoryImpl
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.AuthenticateUseCase
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.LoginUseCase
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthAoi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }




    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRegisterUserCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase{
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUserCase(repository: AuthRepository): AuthenticateUseCase{
        return AuthenticateUseCase(repository)
    }
}























