package com.robertconstantindinescu.my_social_network.presentation.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.domain.models.Activity
import com.robertconstantindinescu.my_social_network.domain.models.Comment
import com.robertconstantindinescu.my_social_network.domain.util.ActivityAction
import com.robertconstantindinescu.my_social_network.domain.util.DateFormatUtil
import com.robertconstantindinescu.my_social_network.presentation.components.ActionRow
import com.robertconstantindinescu.my_social_network.presentation.components.StandardScaffold
import com.robertconstantindinescu.my_social_network.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.presentation.post_detail.Comment
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.*
import com.robertconstantindinescu.my_social_network.presentation.util.Screen
import kotlin.random.Random

@Composable
fun ActivityScreen(
    navController: NavController,
    viewModel: ActivityViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
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

            items(20) {
                ActivityItem(
                    activity = Activity(
                        username = "Robert Constantin",
                        actionType = if (Random.nextInt(2)==0){
                            ActivityAction.LikedPost
                        }else{
                            ActivityAction.CommentedOnPost
                        },
                        formattedTime = DateFormatUtil.timestampToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm"
                        )
                    ),
                    modifier = Modifier.padding(SpaceSmall)

                )
                if (it < 19){
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }

            }
        }
    }

}