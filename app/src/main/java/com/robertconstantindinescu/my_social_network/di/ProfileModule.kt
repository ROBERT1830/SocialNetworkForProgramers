package com.robertconstantindinescu.my_social_network.di

import com.google.gson.Gson
import com.robertconstantindinescu.my_social_network.feature_profile.data.remote.ProfileApi
import com.robertconstantindinescu.my_social_network.feature_profile.data.repository.ProfileRepositoryImpl
import com.robertconstantindinescu.my_social_network.feature_profile.domain.repository.ProfileRepository
import com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {


    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson:Gson): ProfileRepository {
        return ProfileRepositoryImpl(api, gson)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases{
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkillsUseCase = GetSkillsUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository),
            setSkillSelectedUseCase = SetSkillSelectedUseCase()
        )
    }


}