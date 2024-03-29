package com.robertconstantindinescu.my_social_network.feature_auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.presentation.util.asString
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.feature_auth.domain.models.AuthError
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    //navController: NavController,
    onNavigate: (String)-> Unit = {},
    scaffoldState: ScaffoldState,
    onLogin: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {

    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val state = viewModel.loginState.value
    val context = LocalContext.current


    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
                is UiEvent.OnLogin -> {
                    onLogin()
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = SpaceLarge,
                    end = SpaceLarge,
                    top = SpaceLarge,
                    bottom = 50.dp
                )
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.login),
                //we have passed the color y the typo style
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = emailState.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                },
                keyBoardType = KeyboardType.Email,
                error = when (emailState.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                hint = stringResource(R.string.user_name_email)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            StandardTextField(
                text = passwordState.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                },
                hint = stringResource(R.string.password_hint),
                keyBoardType = KeyboardType.Password,
                error = when (passwordState.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    else -> ""
                },
                showPasswordToggle = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))

            Button(
                onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colors.onPrimary
                )
            }

            if (state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
            }


        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    onNavigate(
                        Screen.RegisterScreen.route
                    )
                },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sing_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            style = MaterialTheme.typography.body1,

            )
    }


}


