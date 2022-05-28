package com.robertconstantindinescu.my_social_network.feature_post.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Constants.DEFAULT_PAGE_SIZE
import com.robertconstantindinescu.my_social_network.core.data.remote.PostApi
import retrofit2.HttpException
import java.io.IOException

class PostSource(
    private val api: PostApi,
    private val source: Source
): PagingSource<Int, Post>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        /**
         * Inside here we have to make the retrofit call. So we need a reference
         * to our api in the constructor
         */
        return try {

            //start at page 0 then will automatically get the page.
            //key is the for the page to be loaded. It will be null initially.
            val nextPage = params.key ?: currentPage
            val posts = when(source){
                is Source.Follows -> api.getPostForFollows(
                    page = nextPage,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
                is Source.Profile -> api.getPostForProfile(
                    userId = source.userid,
                    page = nextPage,
                    pageSize = DEFAULT_PAGE_SIZE
                )
            }


            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                //here wesay what the next key is
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    /**
     * We want to differentiate post from a those people we follow and post for a specific profile.
     */

    sealed class Source {
        object Follows: Source()
        data class Profile(val userid: String): Source()
    }
}


































