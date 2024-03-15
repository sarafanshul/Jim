package com.projectdelta.jim.ui.home.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectdelta.jim.data.state.SessionState
import com.projectdelta.jim.ui.common.component.EmptyWorkoutSessionComponent
import com.projectdelta.jim.ui.common.component.WorkoutSessionComponent
import com.projectdelta.jim.ui.home.HomeScreenViewModel
import com.projectdelta.jim.ui.home.component.DayInfoTopBarComponent
import com.projectdelta.jim.ui.home.events.HomeScreenEvent
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.TimeUtil

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
        pageCount = { 100000 } // todo: by default only 50 pages.
    )

    LaunchedEffect(uiState) {// handle ui state changes
        pagerState.animateScrollToPage(uiState.currentDay, 0f)
    }


    LaunchedEffect(pagerState.settledPage) {
        viewModel.handleEvent(
            HomeScreenEvent.PageChangeEvent(pagerState.settledPage)
        )
    }

//    LaunchedEffect(pagerState.targetPage) {
//        viewModel.cacheWorkout(maxOf(pagerState.targetPage - 1, 0))
//        viewModel.cacheWorkout(pagerState.targetPage)
//        viewModel.cacheWorkout(minOf(pagerState.targetPage + 1, pagerState.pageCount))
//    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DayInfoTopBarComponent(
            currentDay = { pagerState.currentPage },
            onDateClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.DateChangeEvent(
                        TimeUtil.getCurrentDayFromEpoch()
                    )
                )
            },
            onBackClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.PreviousDayClickEvent
                )
            },
            onNextClick = {
                viewModel.handleEvent(
                    HomeScreenEvent.NextDayClickEvent
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
            beyondBoundsPageCount = 1, // check this <<<
//            key = {idx ->
//                workoutSessionState = viewModel.getWorkoutByDay(idx)
//            }
        ) { day ->
            // FIXME : This spams db for queries (left, right for every scroll), bad refactor this.

            val workoutSessionState by viewModel.getWorkout(day)
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
                    ) togetherWith fadeOut(animationSpec = tween(200))
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
                            workoutSession = remember{ state.session },
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
