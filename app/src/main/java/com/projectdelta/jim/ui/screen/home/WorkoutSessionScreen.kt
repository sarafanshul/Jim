package com.projectdelta.jim.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.R
import com.projectdelta.jim.data.state.WorkoutSessionState
import com.projectdelta.jim.ui.components.WorkoutSessionComponent
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.Constants.StringRes.NO_WORKOUT_LOG
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.Constants.UI.TEXT_LARGE
import com.projectdelta.jim.util.Constants.UI.TEXT_SMALL_PLUS
import com.projectdelta.jim.util.TimeUtil
import com.projectdelta.jim.util.onClick
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    onClick: onClick,
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
                .fillMaxSize(),
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
                modifier = Modifier.weight(3f)
            )
        }
    }
}

/**
 * Component screen to display when no workout is found ,[WorkoutSessionState.NoSession] state,
 * @param startNewWorkoutOnClick listener for start new workout Button
 * @param copyPreviousWorkoutOnClick listener for copy previous button
 * @param modifier view [Modifier]
 */
@Composable
fun EmptyWorkoutSessionComponent(
    startNewWorkoutOnClick: onClick,
    copyPreviousWorkoutOnClick: onClick,
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
                .weight(0.8f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_NORMAL, PADDING_SMALL)
        )

        Spacer(
            modifier = Modifier
                .weight(0.5f)
        )

        ImageButton(
            text = "Copy Previous Workout",
            painter = painterResource(
                id = R.drawable.baseline_content_copy_24
            ),
            onClick = copyPreviousWorkoutOnClick,
            modifier = Modifier
                .weight(0.87f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_NORMAL, PADDING_SMALL)
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
    onDateClick: onClick,
    onBackClick: onClick,
    onNextClick: onClick,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .weight(9.5f),
            verticalAlignment = Alignment.CenterVertically
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
                    .clickable(onClick = onDateClick),
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
        initialPage = uiState.currentDay
    )

    LaunchedEffect(pagerState.currentPage) {
        launch{
            delay(200) // https://stackoverflow.com/questions/73714228/accompanist-pager-animatescrolltopage-doesnt-scroll-to-next-page-correctly
            if (pagerState.currentPage != uiState.currentDay)
                viewModel.handleEvent(HomeScreenEvent.DateChangeEvent(pagerState.currentPage))
        }
    }

    LaunchedEffect(uiState) {
        if (uiState.currentDay != pagerState.currentPage)
            pagerState.animateScrollToPage(uiState.currentDay, 0f)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DayInfoTopBarComponent(
            currentDay = uiState.currentDay,
            onDateClick = {
                viewModel.handleEvent(HomeScreenEvent.TopBarDateClickEvent)
            },
            onBackClick = {
                viewModel.handleEvent(HomeScreenEvent.TopBarBackClickEvent)
            },
            onNextClick = {
                viewModel.handleEvent(HomeScreenEvent.TopBarNextClickEvent)
            },
            modifier = Modifier
                .weight(0.5f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                )
        )

        val fling = PagerDefaults
            .flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(
                    1
                )
            )

        HorizontalPager(
            pageCount = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier
                .weight(9.5f)
                .fillMaxSize(),
            flingBehavior = fling
        ) { _ ->
            val workoutSessionState by viewModel.workoutSessionState.collectAsState()

            when (workoutSessionState) {
                is WorkoutSessionState.NoSession -> {
                    EmptyWorkoutSessionComponent(
                        startNewWorkoutOnClick = {
                            viewModel.handleEvent(HomeScreenEvent.CreateNewWorkoutEvent)
                        },
                        copyPreviousWorkoutOnClick = {
                            viewModel.handleEvent(HomeScreenEvent.CopyPreviousWorkoutEvent)
                        },
                        modifier = Modifier
                    )
                }

                is WorkoutSessionState.Session -> {
                    WorkoutSessionComponent(
                        workoutSession = (workoutSessionState as WorkoutSessionState.Session).session,
                        modifier = Modifier,
                        onClickWParamAction = {
                            viewModel.handleEvent(HomeScreenEvent.WorkoutSelectedEvent(it))
                            true
                        }
                    )
                }
            }
        }
    }
}
