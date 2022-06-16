package com.robertconstantindinescu.my_social_network.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.PostApi
import com.robertconstantindinescu.my_social_network.feature_profile.data.remote.ProfileApi
import com.robertconstantindinescu.my_social_network.core.data.repository.ProfileRepositoryImpl
import com.robertconstantindinescu.my_social_network.core.domain.repository.ProfileRepository
import com.robertconstantindinescu.my_social_network.core.domain.use_case.ToggleFollowStateForUserUseCase
import com.robertconstantindinescu.my_social_network.feature_post.domain.use_case.LogoutUseCase
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
    fun provideProfileRepository(profileApi: ProfileApi, postApi: PostApi, gson:Gson, sharedPreferences: SharedPreferences): ProfileRepository {
        return ProfileRepositoryImpl(profileApi, postApi  ,gson, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases{
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkillsUseCase = GetSkillsUseCase(repository),
            updateProfileUseCase = UpdateProfileUseCase(repository),
            setSkillSelectedUseCase = SetSkillSelectedUseCase(),
            getPostsForProfileUseCase = GetPostsForProfileUseCase(repository),
            searchUserUseCase = SearchUserUseCase(repository),
            toggleFollowStateForUserUseCase = ToggleFollowStateForUserUseCase(repository),
            logout = LogoutUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideToggleFollowForUseCase(repository: ProfileRepository): ToggleFollowStateForUserUseCase {
        return ToggleFollowStateForUserUseCase(repository)
    }




}