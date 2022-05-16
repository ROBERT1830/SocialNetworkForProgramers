package com.robertconstantindinescu.my_social_network.feature_activity.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.feature_activity.data.remote.ActivityApi
import com.robertconstantindinescu.my_social_network.feature_activity.domain.ActivityRepository
import com.robertconstantindinescu.my_social_network.feature_post.data.paging.ActivitySource
import com.robertconstantindinescu.my_social_network.feature_post.data.paging.PostSource
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(
    private val api: ActivityApi
) : ActivityRepository{

    override val activities: Flow<PagingData<Activity>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            ActivitySource(api)
        }.flow //important to return a flow by .flow This makes the data to be a flow.
}