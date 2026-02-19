package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.Aquamarine
import com.example.water_logging_app.ui.theme.LightGray
import com.example.water_logging_app.ui.theme.inter24
import com.example.water_logging_app.ui.theme.poppins
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val image: Int,
    val title: Int,
    val description: Int,
)
private val OnboardingPageList = listOf(
    OnboardingPage(
        image = R.drawable.ladybottleofwater,
        title = R.string.TrackWithUs,
        description = R.string.AchieveYourGoals
    ),
    OnboardingPage(
        image = R.drawable.manbottleofwater,
        title = R.string.SmartReminders,
        description = R.string.QuickAndEasy
    ),
    OnboardingPage(
        image = R.drawable.bottleofwater,
        title = R.string.EasyToUse,
        description = R.string.StayingHydrated
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreenUi(
    modifier : Modifier,
    currentNavAction : () -> Unit,
) {
    val pagerState = rememberPagerState() { OnboardingPageList.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if(pagerState.currentPage > 0) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    val prevPage = pagerState.currentPage - 1
                                    pagerState.animateScrollToPage(prevPage)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Aquamarine,
                                modifier = Modifier.size(dimensionResource(R.dimen.ArrowBackIconSize))
                            )
                        }
                    }
                    else {
                        Spacer(
                            modifier = Modifier.size(dimensionResource(R.dimen.ArrowBackIconSize))
                        )
                    }
                },
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.container_padding))
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(all = dimensionResource(R.dimen.container_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PaginationSystemUi(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.container_padding),
                            bottom = dimensionResource(R.dimen.container_padding)
                        )
                        .fillMaxWidth(),
                    pagerState = pagerState
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.container_padding)))

                Card(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.container_padding),
                            end = dimensionResource(R.dimen.container_padding)
                        )
                        .height(dimensionResource(R.dimen.ClickableCardHeight))
                        .fillMaxWidth(),
                    onClick = {
                        if(pagerState.currentPage < OnboardingPageList.size - 1) {
                            coroutineScope.launch {
                                val nextPage = pagerState.currentPage + 1
                                pagerState.animateScrollToPage(nextPage)
                            }
                        }
                        else {
                            currentNavAction()
                        }
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = Aquamarine,
                    ),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            if(pagerState.currentPage == OnboardingPageList.size - 1)
                                stringResource(R.string.GetStarted)
                            else {
                                stringResource(R.string.Next)
                            },
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.container_padding))
                .padding(innerPadding)
        ) { page ->
            OnboardingPageContent(
                modifier = Modifier.padding(dimensionResource(R.dimen.container_padding)),
                page = OnboardingPageList[page]
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(
    modifier: Modifier,
    page : OnboardingPage
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(
                    height = 400.dp,
                    width = 400.dp
                )
                .padding(bottom = dimensionResource(R.dimen.extra_container_padding)),
            painter = painterResource(page.image),
            contentDescription = null,
        )
        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = inter24,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding))
        )
        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.labelSmall.copy(
                lineHeight = 12.sp
            )
        )
    }
}

@Composable
private fun PaginationSystemUi(
    modifier : Modifier,
    pagerState : PagerState
) {
    HorizontalPager(
        state = pagerState
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(OnboardingPageList.size) { iteration ->
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
