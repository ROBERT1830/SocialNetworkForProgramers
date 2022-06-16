package com.robertconstantindinescu.my_social_network.feature_post.presentation.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardTextField
import com.robertconstantindinescu.my_social_network.core.presentation.components.StandardToolBar
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceLarge
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceMedium
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceSmall
import com.robertconstantindinescu.my_social_network.core.domain.states.StandardTextFieldState
import com.robertconstantindinescu.my_social_network.core.domain.util.getFileName
import com.robertconstantindinescu.my_social_network.core.presentation.util.CropActivityResultContract
import com.robertconstantindinescu.my_social_network.core.presentation.util.UiEvent
import com.robertconstantindinescu.my_social_network.core.presentation.util.asString
import com.robertconstantindinescu.my_social_network.feature_post.presentation.util.PostConstants.MAX_POST_DESCRIPTION_LENGTH
import com.robertconstantindinescu.my_social_network.feature_post.presentation.util.PostDescriptionError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

@Composable
fun CreatePostScreen(
    onNavigate: (String) -> Unit = {},
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val imageUri = viewModel.chosenImage.value
    val context = LocalContext.current


    /**
     * We want the extension at the point we dont know the uri yet
     */
    val cropActivityLauncher = rememberLauncherForActivityResult(
            contract = CropActivityResultContract(16f, 9f),

            ) {
            //this it comes from the parseResult method from class CropActivityResultContract. You save
            viewModel.onEvent(CreatePostEvent.CropImage(it))
        }


    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent() //this makes the user to pick content and will receive content://+Uri for the content.
            //then to get acces to that content uri you need to use the content resolver

            //with the uri that this gives us we will get the image we want to display
            //we need a state for that uri
        ) {
            //viewModel.onEvent(CreatePostEvent.PickImage(it))
            /**
             * We can pass an input uri and a file name
             */
            cropActivityLauncher.launch(it) // this it will be the input uri for the image


        }


    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {
            when(it){
                is UiEvent.ShowSnackBar -> {
                    //When we navigate up the composable is detroyed and this coroutine of LaunchedEff
                    //is canceleed so that we can't see the snackbar.
                    //We can use a global scope but is not aoptimal. What I sugest is to have
                    //those events up in the tree. but for now use global scope
                    GlobalScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it.uiText.asString(context)
                        )
                    }



                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(id = R.string.create_a_new_post),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground

                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceLarge)
        ) {
            /**
             * This is the box for the iamge. So when we click taht we wanto to open
             * the gallery
             */
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        // /* is to get all image types
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground
                )
                //if the imageUri is not null means that we actuallly pick something
                imageUri?.let { uri ->
                    //display an image.
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = uri,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.descriptionSate.value.text,
                hint = stringResource(id = R.string.description),
                error = when (viewModel.descriptionSate.value.error) {
                    is PostDescriptionError.FieldEmpty -> {
                        stringResource(id = R.string.error_field_empty)
                    }
                    else -> ""
                },
                singleLine = false,
                maxLines = 5,
                maxLength = MAX_POST_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(
                        CreatePostEvent.EnterDescription(it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(
                onClick = {
                    viewModel.onEvent(CreatePostEvent.PostImage)
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier.align(Alignment.End)

            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colors.onPrimary

                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                if (viewModel.isLoading.value){
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                }else{
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }

            }
        }

    }

}



































