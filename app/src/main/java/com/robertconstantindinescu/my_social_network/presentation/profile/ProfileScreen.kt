package com.robertconstantindinescu.my_social_network.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.domain.models.Post
import com.robertconstantindinescu.my_social_network.domain.models.User
import com.robertconstantindinescu.my_social_network.presentation.components.Post
import com.robertconstantindinescu.my_social_network.presentation.profile.components.BannerSection
import com.robertconstantindinescu.my_social_network.presentation.profile.components.ProfileHeaderSection
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.ProfilePictureSizeLage
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.SpaceSmall
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import com.robertconstantindinescu.my_social_network.presentation.util.toPx

/***
 *
 * if x is 5 then the function return 5, because 5 is between 0 and 10.
 * val x = 5f
 * val y = x.coerceIn(
 *  min = 0f,
 *  max = 10f
 * )
 *
 * if x is 5, means that is lower than the minimum value, then the function will assign
 * 7 to z. If x is greater than 10, then will assign 10.
 * val z = x.coerceIn(
 *  min = 7f,
 *  max = 10f
 * )
 *
 * So we did that with the toolbar to has a range. The maximum value is 0, when is collapsed
 *
 *
 */


@Composable
fun ProfileScreen(
    navController: NavController,
    profilePicturesSize: Dp = ProfilePictureSizeLage,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()
    val toolbarState = viewModel.toolbarState.value

    //how much the toolbar is offset
//    var toolbarOffsetY by remember {
//        mutableStateOf(0f)
//    }
    val iconHorizontalCentralLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx()/4f -
            (profilePicturesSize / 4f).toPx() + SpaceSmall.toPx())/ 2f
    //check for the firt item in the list. We only want to scroll down the banner section when the first
    //item from the list is visible so that not appears when user performs large scroll
    val isFirstItemVisible = lazyListState.firstVisibleItemIndex == 0

    val iconSizeExpanded = 35.dp

    //height of the toolbar in its collapsed state.
    val toolbarHeightCollapsed = 75.dp

    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - profilePicturesSize / 2f) / 2f
    }
    //maximum offset to center image
    val iconCollapsedOffsetY = remember{
        (toolbarHeightCollapsed -  iconSizeExpanded ) / 2f
    }

    //image banner is 1200 * 480 --> to determine the height get the aspect ratio (1200/480 = 2.5f)
    //and divide the screen width with it.
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    //by using "=" remember the value is saved and does not calculate again when recomposition take place
    //used to perform some space between banner and info above.
    val toolbarHeightExpanded = remember {
        bannerHeight + profilePicturesSize
    }
    Log.d("toolbarHeightExpanded", toolbarHeightExpanded.toString())
    //the maximum amount of pixels that the banner will go outside the upper screen
    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }
    Log.d("maxOffset", maxOffset.toString())
    //ratio to multiply the banner height. will be changed so because of that we use by.
    var expandedRatio by remember {
        mutableStateOf(1f)
    }

    /**
     * The NestedScrollConnection listen scroll events in children. So we attach the nestedScrollConnection
     * to our box that contains the scrollable item. And this nestedScrollConnection will listen to
     * scroll events in the lazy column and the column with the banner section and the image.
     *
     * With NestedScrollConnection we have a bunch of option. On the one hand we have the available
     * offset (this will give us how many pixels we actually scrolled in y axis. That is relevant because
     * we need that amount of pixels to apply those pixels to our toolbar height for collapse)
     *
     * We return an offset.Zero onPreScroll function. That gives us the option to actually consume
     * some kind of that scroll strength. For example if we return Offset(x = 0f, y = available.y * 0.5f)
     * we will se that we need to scroll much more to collapse the toolbar and it does not move that strongly (fast) because
     * only half of the scroll strength so to speak is applied from the scrolled children. So what is going on
     * here is that we are getting half of the y axis scroll amount. By leve it at Zero the speed will be by default.
     *
     * When we enter the screen, expandedRatio by default is 1. So when the code
     * reaches the column where the height is applied we will se that the operation will
     * be bannerHeight * 1. So because this still in range of  minimumValue = toolbarHeightCollapsed,
     * and maximumValue = bannerHeight, the height of that column will be bannerHeight.
     *
     * Now when we start scrolling down the expanded ratio should be decreased. How
     * this happens? The delta value will be updated with + numbers when scrolling down
     * and + numbers when scrolling up by available (is the rate of how many pixels
     * the user abarca with a certain distance of scroll finger).
     *
     * According to this value the newOffset is updated each and every timea so is the
     * toolbarOffsetY. With this value the expanded ratio is calculated and used in the column height
     * for the bannerSection.
     *
     * When starting toolbarOffsetY is 0, and if the user scrolls up a bit for example the
     * delta could be -10.98432. In that case, 0 + (-10) = -10. Now the coerceIn function checks
     * if the value of newOffset is bettween a range. If it is, then return the same value, in
     * that case -10.
     *
     * The calculation of the expandedRatio will take place. -10 + 225(maxOffset) / 225 = 0.955
     *
     * So when apply the bannerSection height the bannerHeight (green iamge) will be multiplied by
     * this ratio. So that the height changes.
     *
     */
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                //amount of pixels from a scroll.
                val delta = available.y
                //only perform scroll on banner section and modify offset when the scroll
                //is in the actual list.
                if (delta > 0f && lazyListState.firstVisibleItemIndex != 0) {
                    return Offset.Zero
                }
                Log.d("delta", delta.toString())
                //it seems that in the callback function we cant user the toolbarstete we defined before.
                //i dont know wy. We have to call explicitly the viewmodel
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                Log.d("newOffset", (toolbarState.toolbarOffsetY + delta).toString())
                /*coerceIn --> if newOffset is */
                viewModel.setToolbarOffsetY(newOffset.coerceIn(
                    //if newOffset is between min max, just stay where it is but if newOffset is
                    //grater than 0 then will return 0.
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f

                ))
                //totalToolbarOffsetY += toolbarOffsetY
                viewModel.setExpandedRatio((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) / maxOffset.toPx())
                println("EXPANDED RATION: $expandedRatio")
                return Offset.Zero
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            //this is only to listen for scrolling on children. In our case, the amount of pixels.
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            //have info about items.
            state = lazyListState
        ) {
            //Header
//            item{
//                BannerSection(imageModifier = Modifier
//                    .fillMaxWidth())
//
//            }
            item {
                Spacer(modifier = Modifier.height(toolbarHeightExpanded - profilePicturesSize / 2f))
            }
            item {
                ProfileHeaderSection(
                    user = User(
                        profilePicture = "",
                        username = "Robert Constantin",
                        description = "Lorem ipsum es el texto que se usa habitualmente en diseño " +
                                "gráfico en demostraciones de tipografías o de borradores de diseño " +
                                "para probar el diseño visual antes de insertar el texto final",
                        followerCount = 234,
                        followingCount = 432,
                        postCount = 23
                    ),
                    onAddEditClick = {

                    }
                )
            }

            items(20) {
                Spacer(
                    modifier = Modifier
                        .height(SpaceMedium)
                    //.offset(y = -profilePicturesSize / 2f)
                )
                Post(
                    post = Post(
                        username = "Robert Constantin",
                        imageUrl = "",
                        profilePicture = "",
                        description = "sadasd asdasd asfsd fsd g sdg sd g sf gfs g df gdf g dsf gs dfg " +
                                "sdfgsdfgsdfgsdfg sdfg sdfg aerhwet hd b",
                        likeCount = 17,
                        commentCount = 7
                    ),
                    showProfileImage = false,
                    onPostClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    }
                )

            }
        }
        //because all items are placed in a box, there are no separation between them
        //so we need to have a spacer on top of ProfileHeaderSection.
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            BannerSection(
                modifier = Modifier
                    .height(
                        (bannerHeight * toolbarState.expandedRatio).coerceIn(
                            minimumValue = toolbarHeightCollapsed,
                            maximumValue = bannerHeight
                        )
                    ),
                leftIconModifier = Modifier
                    .graphicsLayer {
                        translationY = (1f - toolbarState.expandedRatio) * -iconCollapsedOffsetY.toPx()


                        translationX = (1f - toolbarState.expandedRatio) * iconHorizontalCentralLength
                    },
                rightIconModifier = Modifier
                    .graphicsLayer {
                    translationY = (1f - toolbarState.expandedRatio) * -iconCollapsedOffsetY.toPx()
                    translationX = (1f - toolbarState.expandedRatio) * -iconHorizontalCentralLength
                }
            )
            Image(
                painter = painterResource(id = R.drawable.robert),
                contentDescription = stringResource(
                    id = R.string.profile_image
                ),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .graphicsLayer {
                        translationY = -profilePicturesSize.toPx() / 2f -
                                (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0f
                        )
                        //the size we always want. SO if we are at the very top
                        //we want to add 0 on top of that then we stay at the
                        //(ProfilePictureSizeLage.toPx() / 2f) size, i men, half of the initial
                        //size.
                        val scale = 0.5f + (toolbarState.expandedRatio * 0.5f)
                        scaleX = scale
                        scaleY = scale
                    }
                    .size(profilePicturesSize)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface,
                        shape = CircleShape
                    )
            )
        }
    }


    //Column(modifier = Modifier.fillMaxSize()) {


//            StandardToolBar(
//                navController = navController,
//                title = {
//                    Text(
//                        text = stringResource(id = R.string.your_profile),
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colors.onBackground
//
//                    )
//                },
//                modifier = Modifier.fillMaxWidth(),
//                showBackArrow = false
//            )

//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//                //Header
//                item{
//
//
//                }
//                item {
//                    ProfileHeaderSection(
//                        user = User(
//                            profilePicture = "",
//                            username = "Robert Constantin",
//                            description = "Lorem ipsum es el texto que se usa habitualmente en diseño " +
//                                    "gráfico en demostraciones de tipografías o de borradores de diseño " +
//                                    "para probar el diseño visual antes de insertar el texto final",
//                            followerCount = 234,
//                            followingCount = 432,
//                            postCount = 23
//                        ),
//                        onAddEditClick = {
//
//                        }
//                    )
//                }
//
//                items(20) {
//                    Spacer(modifier = Modifier
//                        .height(SpaceMedium)
//                        .offset(y = -ProfilePictureSizeLage / 2f)
//                    )
//                    Post(
//                        post = Post(
//                            username = "Robert Constantin",
//                            imageUrl = "",
//                            profilePicture = "",
//                            description = "sadasd asdasd asfsd fsd g sdg sd g sf gfs g df gdf g dsf gs dfg " +
//                                    "sdfgsdfgsdfgsdfg sdfg sdfg aerhwet hd b",
//                            likeCount = 17,
//                            commentCount = 7
//                        ),
//                        showProfileImage = false,
//                        onPostClick = {
//                            navController.navigate(Screen.PostDetailScreen.route)
//                        },
//                        modifier = Modifier.offset(y = -ProfilePictureSizeLage / 2f)
//                    )
//
//                }
//            }
    //      }


}