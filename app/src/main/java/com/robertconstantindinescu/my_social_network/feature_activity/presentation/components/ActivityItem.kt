package com.robertconstantindinescu.my_social_network.feature_activity.presentation.activity

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Activity
import com.robertconstantindinescu.my_social_network.core.presentation.ui.theme.SpaceSmall
import com.robertconstantindinescu.my_social_network.core.util.Screen
import com.robertconstantindinescu.my_social_network.domain.util.ActivityType

//all that stuff will be saved in backend
@Composable
fun ActivityItem(
    activity: Activity,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.onSurface,
        elevation = 5.dp
    ){
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(SpaceSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fillerText = when(activity.activityType){
                is ActivityType.LikedPost ->{
                    stringResource(id = R.string.liked)
                }
                is ActivityType.CommentedOnPost -> {
                    stringResource(id = R.string.commented_on)
                }
                is ActivityType.FollowedUser -> {
                    stringResource(id = R.string.followed_you)
                }
                is ActivityType.LikedComment -> {

                }
                else -> {stringResource(id = R.string.liked)}
            }
            val actionText = when(activity.activityType){
                is ActivityType.LikedPost ->{
                    stringResource(id = R.string.your_post)
                }
                is ActivityType.CommentedOnPost -> {
                    stringResource(id = R.string.your_post)
                }
                is ActivityType.FollowedUser -> ""

                is ActivityType.LikedComment -> {
                    stringResource(id = R.string.your_comment)
                }
                else -> {stringResource(id = R.string.liked)}
            }

            /**
             * We can take parts of our text and asign a tag to them. And then Say we have a ClickableText
             * with that tag and make it clicable. .
             */
            /**Clickable text string that is asociated with a clickable composable text
             * that has this tag. */
            val annotatedText = buildAnnotatedString {
                val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
                //Attach the given annotation to any appended text until a corresponding pop is called.
                pushStringAnnotation(
                    tag = "username",
                    annotation = "username"
                )

                withStyle(boldStyle){
                    append(activity.username)
                }
                pop()
                append(" $fillerText ")

                pushStringAnnotation(
                    tag = "parent",
                    annotation = "parent"
                )


                withStyle(boldStyle){
                    append(actionText)
                }
            }

            ClickableText(
                text = annotatedText,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onBackground
                )
            ,
                // here the index 0 i the person who didi an action and the index 1 is the parent in which the action was performed.
                onClick = { offset ->
                    //Name
                    annotatedText.getStringAnnotations(
                        tag = "username",// tag which you used in the buildAnnotatedString
                        start = offset,
                        end = offset
                    ).firstOrNull().let { annotation ->
                        //do your stuff when it gets clicked
                        //clicked on user
                        onNavigate(Screen.ProfileScreen.route + "?userId=${activity.userId}")


                    }
                    //Parent
                    annotatedText.getStringAnnotations(
                        tag = "parent",// tag which you used in the buildAnnotatedString
                        start = offset,
                        end = offset
                    ).firstOrNull().let { annotation ->
                        //clicked on user
                        onNavigate(Screen.ProfileScreen.route + "?userId=${activity.userId}")
                        Screen.PostDetailScreen.route + "/${activity.parentId}"

                    }
                }

            )
            Text(
                text = activity.formattedTime.toString(),
                textAlign = TextAlign.Right,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )

        }


    }

}































