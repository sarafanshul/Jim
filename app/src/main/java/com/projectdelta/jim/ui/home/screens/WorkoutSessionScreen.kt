package com.projectdelta.jim.ui.home.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.R
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.ui.common.component.WorkoutSessionComponent
import com.projectdelta.jim.ui.home.HomeScreenViewModel
import com.projectdelta.jim.ui.home.events.HomeScreenEvent
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.Constants.StringRes.NO_WORKOUT_LOG
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.Constants.UI.TEXT_LARGE
import com.projectdelta.jim.util.Constants.UI.TEXT_SMALL_PLUS
import com.projectdelta.jim.util.TimeUtil
import com.projectdelta.jim.util.callback

/**
 * Image clickable card with transparent background
 * @param text display text
 * @param painter [Painter] object with resource info
 * @param onClick click event listener
 * @param modifier view's [Modifier]
 * @param contentDescription bruh :(
 */
@Composable
fun ImageButton(
    text: String,
    painter: Painter,
    onClick: callback,
    modifier: Modifier = Modifier,
    contentDescription: String = ""
) {
    Card(
        shape = RoundedCornerShape(Constants.UI.ROUND_RADIUS_NORMAL),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        modifier = modifier
            .background(Color.Transparent, RoundedCornerShape(Constants.UI.ROUND_RADIUS_NORMAL)),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = PADDING_SMALL,
                    vertical = PADDING_NORMAL
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                contentDescription = contentDescription,
                modifier = Modifier
                    .weight(7f)
                    .fillMaxSize()
            )
            Text(
                text = text,
                fontWeight = FontWeight.Light,
                fontSize = TEXT_SMALL_PLUS,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
            )
        }
    }
}

/**
 * Component screen to display when no workout is found ,[SessionState.Empty] state,
 * @param startNewWorkoutOnClick listener for start new workout Button
 * @param copyPreviousWorkoutOnClick listener for copy previous button
 * @param modifier view [Modifier]
 */
@Composable
fun EmptyWorkoutSessionComponent(
    startNewWorkoutOnClick: callback,
    copyPreviousWorkoutOnClick: callback,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = TEXT_LARGE
                    )
                ) {
                    append(NO_WORKOUT_LOG)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .weight(8f),
            textAlign = TextAlign.Center
        )

        ImageButton(
            text = "Start New Workout",
            painter = painterResource(
                id = R.drawable.baseline_add_24
            ),
            onClick = startNewWorkoutOnClick,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_SMALL, PADDING_SMALL)
        )

        Spacer(
            modifier = Modifier
                .weight(0.15f)
        )

        ImageButton(
            text = "Copy Previous Workout",
            painter = painterResource(
                id = R.drawable.baseline_content_copy_24
            ),
            onClick = copyPreviousWorkoutOnClick,
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_SMALL, PADDING_SMALL)
        )
    }
}

/**
 * A top app-bar look alike with two arrows (<, >) and date descriptor.
 * @param currentDay [Int] input for Day element
 * @param onDateClick date click listener
 * @param onBackClick listener for back button
 * @param onNextClick listener for next button
 * @param modifier view [Modifier]
 */
@Composable
fun DayInfoTopBarComponent(
    currentDay: Int,
    onDateClick: callback,
    onBackClick: callback,
    onNextClick: callback,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .weight(9.5f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.round_arrow_back_ios_new_24,
                ),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(onClick = onBackClick),
                contentDescription = "button day prev"
            )

            Text(
                text = TimeUtil.getDayFormatted(currentDay),
                modifier = Modifier
                    .weight(8f)
                    .fillMaxSize()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = onDateClick)
                    .semantics { role = Role.Button },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Image(
                painter = painterResource(
                    id = R.drawable.round_arrow_forward_ios_24,
                ),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(onClick = onNextClick),
                contentDescription = "button day next"
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .weight(0.5f),
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
    }
}

/**
 * Workout Session Main Screen
 */
@Composable
fun WorkoutSessionScreen(
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.homeScreenState.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = uiState.currentDay,
        pageCount = { Int.MAX_VALUE }
    )

    LaunchedEffect(uiState) {// handle ui state changes
        pagerState.animateScrollToPage(uiState.currentDay, 0f)
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCurrentDaySilently(pagerState.currentPage)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DayInfoTopBarComponent(
            currentDay = pagerState.currentPage,
            onDateClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.DateChangeEvent(
                        TimeUtil.getCurrentDayFromEpoch()
                    )
                )
            },
            onBackClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.DateChangeEvent(
                        pagerState.currentPage - 1
                    )
                )
            },
            onNextClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.DateChangeEvent(
                        pagerState.currentPage + 1
                    )
                )
            },
            modifier = Modifier
                .weight(0.5f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                )
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(9.5f)
                .fillMaxSize()
                .padding(bottom = PADDING_SMALL),
        ) { day ->
            // FIXME : This spams db for queries (left, right for every scroll), bad refactor this.
            val workoutSessionState by viewModel.getWorkoutByDay(day)
                .collectAsState(SessionState.Empty)

            AnimatedContent(
                targetState = workoutSessionState,
                label = "WorkoutSessionHomeAnimation",
                transitionSpec = {
                    // enter animation: (fade in + slide up),
                    // exit animation: (fade out)
                    fadeIn() + slideInVertically(
                        animationSpec = tween(400),
                        initialOffsetY = { fullHeight -> fullHeight }
                    ) with fadeOut(animationSpec = tween(200))
                }
            ) { state ->
                when (state) {
                    is SessionState.Empty -> {
                        EmptyWorkoutSessionComponent(
                            startNewWorkoutOnClick = {
                                viewModel.handleEvent(HomeScreenEvent.CreateNewWorkoutEvent)
                            },
                            copyPreviousWorkoutOnClick = {
                                viewModel.handleEvent(HomeScreenEvent.LaunchCalendarEvent(copy = true))
                            },
                            modifier = Modifier
                        )
                    }

                    is SessionState.Session -> {
                        WorkoutSessionComponent(
                            workoutSession = state.session,
                            modifier = Modifier
                                .fillMaxHeight(),
                            onClickWParamAction = {
                                viewModel.handleEvent(HomeScreenEvent.WorkoutSelectedEvent(it))
                            }
                        )
                    }
                }
            }
        }
    }
}
