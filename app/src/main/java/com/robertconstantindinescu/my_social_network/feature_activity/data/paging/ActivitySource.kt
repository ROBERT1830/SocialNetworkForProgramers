package com.robertconstantindinescu.my_social_network.feature_post.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Constants.DEFAULT_PAGE_SIZE
import com.robertconstantindinescu.my_social_network.core.data.remote.PostApi
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.feature_activity.data.remote.ActivityApi
import retrofit2.HttpException
import java.io.IOException

class ActivitySource(
    private val api: ActivityApi,
): PagingSource<Int, Activity>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Activity> {
        /**
         * Inside here we have to make the retrofit call. So we need a reference
         * to our api in the constructor
         */
        return try {

            //start at page 0 then will automatically get the page.
            //key is the for the page to be loaded. It will be null initially.
            val nextPage = params.key ?: currentPage
            val activities = api.getActivities(
                page = nextPage,
                pageSize = DEFAULT_PAGE_SIZE
            )
            LoadResult.Page(
                data = activities.map { it.toActivity() },
                prevKey = if (nextPage == 0) null else nextPage - 1,
                //here wesay what the next key is
                nextKey = if (activities.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Activity>): Int? {
        return state.anchorPosition
    }
}


































