package com.robertconstantindinescu.my_social_network.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.Post
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.core.util.sendSharePostIntent
import com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed.MainFeedEvent
import com.robertconstantindinescu.my_social_network.feature_post.presentation.main_feed.MainFeedViewModel
import com.robertconstantindinescu.my_social_network.feature_post.presentation.person_list.PostEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MainFeedScreen(
    //navController: NavController,
    imageLoader: ImageLoader,
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MainFeedViewModel = hiltViewModel()
) {
    //val posts = viewModel.posts.collectAsLazyPagingItems()
    val paginState = viewModel.pagingState.value
    val context = LocalContext.current
    //val state = viewModel.state.value
    val scope = rememberCoroutineScope() //coroutine for displaying the toeas

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PostEvent.OnLiked -> {
                    /**/
                    //posts.refresh()
                }
            }
        }

    }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.your_feed),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navAction = {
                IconButton(onClick = {
                    onNavigate(Screen.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Add",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
//            if (state.isLoadingFirstTime){
//                CircularProgressIndicator(modifier = Modifier.align(Center))
//            }
            LazyColumn {
                //get items of lazypagin items because we are working with it ()pagin3
                items(paginState.items.size) { i ->
                    val post = paginState.items[i]
                    //if index is grater than the size of the items list and we didn't reach the end we are not loading
                    if (i >= paginState.items.size - 1 && !paginState.endReached && !paginState.isLoading) {
                        viewModel.loadNextPosts()
                    }
                    Post(
                        post = post,
                        imageLoader = imageLoader,
                        onUsernameClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                        },
                        onPostClick = {
                            onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                        },
                        onCommentClick ={
                            onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyBoard=true")
                        },
                        onLikeClick = {
                            viewModel.onEvent(MainFeedEvent.LikedPost(post.id))
                            //posts.refresh()
                        },
                        onSharedClick = {
                            context.sendSharePostIntent(post.id)
                        }
                    )
                    //if we are not at the list item add some space.
                    if (i < paginState.items.size - 1) {
                        Spacer(modifier = Modifier.height(SpaceLarge))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }
//                posts.apply {
//                    //This is in the lazylst scope
//                    /**
//                     * How we detext if the page is actually loaded
//                     */
//                    when {
//                        //this is for the first time for the entire list
//                        loadState.refresh is LoadState.Loading -> {
//                            viewModel.onEvent(MainFeedEvent.LoadedPage)
//                        }
//                        //append more posts to the adapter at the first page
//                        loadState.append is LoadState.Loading -> {
//                            viewModel.onEvent(MainFeedEvent.LoadMorePosts)
//                        }
//                        //load the page with our post
//                        loadState.append is LoadState.NotLoading -> {
//                            viewModel.onEvent(MainFeedEvent.LoadedPage)
//                        }
//                        loadState.append is LoadState.Error -> {
//                            scope.launch {
//                                scaffoldState.snackbarHostState.showSnackbar(
//                                    message = "Error"
//                                )
//                            }
//
//                        }
//                    }
//                }
            }
            //represents the loading for request and append new posts.

            if (paginState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

        }


    }


}