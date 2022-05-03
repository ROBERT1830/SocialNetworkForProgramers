package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.Post
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed.MainFeedEvent
import com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed.MainFeedViewModel
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val state = viewModel.state.value
    val scope = rememberCoroutineScope() //coroutine for displaying the toeas

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolBar(
            navController = navController,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navAction = {
                IconButton(onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()){
            if (state.isLoadingFirstTime){
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
            LazyColumn{
                //get items of lazypagin items because we are working with it ()pagin3
                items(posts){ post ->
                    Post(
                        post = com.robertconstantindinescu.my_social_network.core.domain.models.Post(
                            username = post?.username ?: "",
                            imageUrl = post?.imageUrl ?: "",
                            profilePicture = post?.profilePicture ?:"",
                            description = post?.description ?:"",
                            likeCount = post?.likeCount ?:0,
                            commentCount = post?.commentCount?:0
                        ),
                        onPostClick = {
                            navController.navigate(Screen.PostDetailScreen.route)
                        }
                    )
                }
                //represents the loading for request and append new posts.
                item {
                    if (state.isLoadingNewPosts){
                        CircularProgressIndicator(modifier = Modifier.align(BottomCenter))
                    }
                }
                posts.apply {
                    //This is in the lazylst scope
                    /**
                     * How we detext if the page is actually loaded
                     */
                    when {
                        //this is for the first time for the entire list
                        loadState.refresh is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        //append more posts to the adapter at the first page
                        loadState.append is LoadState.Loading -> {
                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
                        }
                        //load the page with our post
                        loadState.append is LoadState.NotLoading -> {
                            viewModel.onEvent(MainFeedEvent.LoadedPage)
                        }
                        loadState.append is LoadState.Error -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Error"
                                )
                            }

                        }
                    }
                }
            }
        }




    }


}