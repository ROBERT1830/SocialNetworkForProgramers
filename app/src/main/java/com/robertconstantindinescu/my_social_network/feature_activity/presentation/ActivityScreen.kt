package com.robertconstantindinescu.my_social_network.feature_activity.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.core.util.DateFormatUtil
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.*
import com.robertconstantindinescu.my_social_network.domain.util.ActivityType
import com.robertconstantindinescu.my_social_network.feature_activity.presentation.activity.ActivityItem
import kotlin.random.Random

@Composable
fun ActivityScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
) {


    val state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()
    //a box to show progress bar
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            StandardToolBar(
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = stringResource(id = R.string.your_activity),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground

                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                //paddind for all sides
                contentPadding = PaddingValues(SpaceMedium)
            ) {

                items(activities) { activity ->
                    activity?.let {
                        ActivityItem(
                            activity = Activity(
                                activity.userId,
                                activityType = activity.activityType,
                                formattedTime = activity.formattedTime,
                                parentId = activity.parentId,
                                username = activity.username
                            ),
                            modifier = Modifier.padding(SpaceSmall),
                            onNavigate = onNavigate

                        )
                    }

//                    if (it < 19){
//                        Spacer(modifier = Modifier.height(SpaceMedium))
//                    }

                }
            }

        }
        if (state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }



}