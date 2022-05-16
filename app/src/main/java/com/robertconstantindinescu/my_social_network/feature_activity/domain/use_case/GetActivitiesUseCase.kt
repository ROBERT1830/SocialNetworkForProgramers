package com.robertconstantindinescu.my_social_network.feature_activity.domain.use_case

import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.feature_activity.domain.ActivityRepository
import kotlinx.coroutines.flow.Flow

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<PagingData<Activity>>{
        return repository.activities
    }
}