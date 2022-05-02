package com.robertconstantindinescu.my_social_network.feature_profile.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.User
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.components.UserProfileItem
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.IconSizeMedium
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        StandardToolBar(
            navController = navController,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.search_for_users),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            }
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)) {
            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.searchState.value.text,
                hint = stringResource(id = R.string.search),
                error = "",
                leadingIcon = Icons.Default.Search,
                onValueChange = {
                    viewModel.setSearchState(
                        StandardTextFieldState(text = it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(10){
                    UserProfileItem(
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
                        actionIcon = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = null,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }

}