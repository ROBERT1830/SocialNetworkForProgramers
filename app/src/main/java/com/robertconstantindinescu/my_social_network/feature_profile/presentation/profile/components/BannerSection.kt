package com.robertconstantindinescu.my_social_network.feature_profile.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceSmall
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import com.robertconstantindinescu.my_social_network.presentation.util.toPx

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    bannerUrl: String? = null,
    topSkills: List<Skill> = emptyList(),
    shouldShowGitHub: Boolean = false,
    shouldShowInstagram: Boolean = false,
    shouldShowLinkedIn: Boolean = false,

    //onIconGroupWidthChange: (Int) -> Unit = {} ,
    onGitHubClick: () -> Unit = {},
    onInstagramClick: () -> Unit = {},
    onLinkedInClick: () -> Unit = {}
) {


    BoxWithConstraints(
        modifier = modifier
    ) {

        Image(
            painter = rememberImagePainter(
                data = bannerUrl,
                imageLoader = imageLoader
            ),
//            painter = rememberAsyncImagePainter(
//                ImageRequest.Builder(LocalContext.current).data(data = bannerUrl)
//                    .apply(block = fun ImageRequest.Builder.() {
//                        crossfade(true)
//                    }).build()
//            ),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
            //if we apply here the background then is below the image

        )
        //create an composable for the shadow that should be on top
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    //transition from transparent to black
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        //make the parent box constrained to get the height. The gradients will start at icon height
                        startY = constraints.maxHeight - iconSize.toPx() * 2f

                    )
                )
        )

        Row(
            leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(SpaceSmall)
        ) {
            topSkills.forEach { skill ->
                Spacer(modifier = Modifier.width(SpaceSmall))
                Image(

                    painter = rememberImagePainter(
                        data = skill.imageUrl,
                        imageLoader = imageLoader
                    ),
//                    painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
//                        .data(data = skill.imageUrl).build(),
//                        imageLoader = ImageLoader.Builder(LocalContext.current)
//                            .components {
//                                add(SvgDecoder.Factory())
//                            }
//                            .build()
//                    ),
//                    painter = rememberAsyncImagePainter(
//                        ImageRequest.Builder(LocalContext.current).data(data = skill.imageUrl)
//                            .apply(block = fun ImageRequest.Builder.() {
//                                crossfade(true)
//                            })
//                            .build()
//                    ),
                    contentDescription = null,
                    modifier = Modifier.height(iconSize)
                )
            }

        }

        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(SpaceSmall)
        ) {

            if (shouldShowGitHub) {
                IconButton(
                    onClick = { onGitHubClick() },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_github_icon_1),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }

            if (shouldShowInstagram) {
                IconButton(
                    onClick = { onInstagramClick() },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_instagram_glyph_1),
                        contentDescription = "Instagram",
                        modifier = Modifier.size(iconSize)

                    )
                }
            }

            if (shouldShowLinkedIn) {
                IconButton(
                    onClick = { onLinkedInClick() },
                    modifier = Modifier.size(iconSize)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_linkedin_icon_1),
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }


        }


    }

//    Column(modifier = modifier) {
//    }


}


@Composable
fun IconSection(
    icons: List<Painter>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

    }

}