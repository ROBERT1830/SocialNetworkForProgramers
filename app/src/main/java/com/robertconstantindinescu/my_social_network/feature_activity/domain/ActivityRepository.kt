package com.robertconstantindinescu.my_social_network.feature_activity.domain

import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    val activities: Flow<PagingData<Activity>>


}