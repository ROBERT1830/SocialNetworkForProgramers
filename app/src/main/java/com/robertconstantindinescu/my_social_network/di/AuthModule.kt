package com.robertconstantindinescu.my_social_network.di

import com.robertconstantindinescu.my_social_network.feature_auth.data.remote.AuthApi
import com.robertconstantindinescu.my_social_network.feature_auth.data.remote.AuthApi.Companion.BASE_URL
import com.robertconstantindinescu.my_social_network.feature_auth.data.repository.AuthRepositoryImpl
import com.robertconstantindinescu.my_social_network.feature_auth.domain.repository.AuthRepository
import com.robertconstantindinescu.my_social_network.feature_auth.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthAoi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegisterUserCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }
}