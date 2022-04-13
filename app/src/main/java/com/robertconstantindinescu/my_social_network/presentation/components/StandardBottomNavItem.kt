package com.robertconstantindinescu.my_social_network.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.HintGray
import com.robertconstantindinescu.my_social_network.presentation.ui.theme.SpaceMedium
import kotlin.jvm.Throws

//RowScope here because we only have access to BottomNavigationItem in the RowScope that gives us
//BottomNavigation
@Composable
@Throws(IllegalArgumentException::class)
fun RowScope.StandardBottomNavItem(
    icon: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    alertCount: Int? = null,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = HintGray,
    enabled: Boolean = true,
    onClick: () -> Unit

){
    //will crash here if the alertCount is not grater than 0
//    alertCount?.let {
//        require(alertCount >= 0)
//    }

    if (alertCount != null && alertCount<0){
        throw IllegalArgumentException("Alert count not grater thatn 0")
    }

    BottomNavigationItem(
        selected = selected,
        onClick = { onClick()},
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        icon = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
                .drawBehind {
                    if (selected){
                        drawLine(
                            color = if (selected) selectedColor
                            else unselectedColor,
                            //offset line draw at x0 and and height (starts by top left in coordinate system)
                            start = Offset(size.width/2f - 15.dp.toPx(), size.height),
                            end = Offset(size.width/2f + 15.dp.toPx(), size.height),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }


                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.align(Alignment.Center)
                )
                if (alertCount != null){
                    //if alert is more then 100 display 99+
                    val alertText = if (alertCount > 99){
                        "99+"
                    }else{
                        alertCount.toString()
                    }

                    Text(
                        text = alertText,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(15.dp)
                            .size(15.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary)

                    )
                }
            }
        }
    )

}