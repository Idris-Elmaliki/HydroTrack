package com.example.water_logging_app.ui.subscreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.LightGray

/*
* This sub-screen is used within all the screens that have a pagination system.
*
* This simply just shows the page indicator.
*/

@Composable
fun <T> PaginationSystemUi(
    modifier : Modifier,
    pagerState : PagerState,
    pagerList : List<T> // the screens can pass different types of variable lists
) {
    HorizontalPager(
        state = pagerState
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pagerList.size) { iteration ->
                val isSelected = (pagerState.currentPage == iteration)

                val width by animateDpAsState(
                    targetValue = if (isSelected) 35.dp else 25.dp,
                    animationSpec = tween(durationMillis = 300),
                    label = "width"
                )

                val color by animateColorAsState(
                    targetValue = if (isSelected) {
                        Aquamarine
                    } else {
                        LightGray
                    },
                    animationSpec = tween(durationMillis = 300),
                    label = "color"
                )

                Box(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.PageIndicatorIconSize))
                        .height(10.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(color)
                ) {}
            }
        }
    }
}
