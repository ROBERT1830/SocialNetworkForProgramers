package com.robertconstantindinescu.my_social_network.core.util

class DefaultPaginator<T>(
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextPage: Int) -> Resource<List<T>>,
    private val onError: suspend (UiText) -> Unit,
    private val onSuccess: (items: List<T>) -> Unit
): Paginator<T> {

    private var page = 0

    override suspend fun loadNextItems() {
        onLoadUpdated(true)
        //after loading perform the request
        when(val result = onRequest(page)) {
            is Resource.Success -> {
                //get actual data, the resoult which is a list o T.When we paginate always deal with list of items
                val items = result.data ?: emptyList()
                //increase and in the next iteration we pass the next page8
                page++
                onSuccess(items)
                onLoadUpdated(false)
            }
            is Resource.Error -> {
                onError(result.uiText ?: UiText.unknownError())
                onLoadUpdated(false)
            }
        }
    }
}