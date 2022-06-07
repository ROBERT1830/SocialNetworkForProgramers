package com.robertconstantindinescu.my_social_network.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}