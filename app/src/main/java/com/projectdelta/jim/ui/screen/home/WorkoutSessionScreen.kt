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
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectdelta.jim.R
import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.model.WorkoutSession
import com.projectdelta.jim.data.model.WorkoutSet
import com.projectdelta.jim.data.state.WorkoutSessionState
import com.projectdelta.jim.ui.components.WorkoutSessionComponent
import com.projectdelta.jim.ui.events.WorkoutSessionScreenEventsHandler
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants
import com.projectdelta.jim.util.Constants.StringRes.NO_WORKOUT_LOG
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.Constants.UI.TEXT_LARGE
import com.projectdelta.jim.util.Constants.UI.TEXT_SMALL_PLUS
import com.projectdelta.jim.util.TimeUtil
import com.projectdelta.jim.util.launchIO
import com.projectdelta.jim.util.onClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Image clickable card with transparent background
 * @param text display text
 * @param painter [Painter] object with resource info
 * @param onClick for card's on click events
 * @param modifier view's [Modifier]
 * @param contentDescription bruh :(
 */
@Composable
fun ImageButton(
    text: String,
    painter: Painter,
    onClick: onClick<Unit>,
    modifier: Modifier = Modifier,
    contentDescription: String =  ""
){
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
        onClick = { onClick.invoke(Unit) }
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
 * @param eventsHandler [WorkoutSessionScreenEventsHandler] instance ot pass following events
 * - [WorkoutSessionScreenEventsHandler.addNewWorkout]
 * - [WorkoutSessionScreenEventsHandler.copyPreviousWorkout]
 * @param modifier view [Modifier]
 */
@Composable
fun EmptyWorkoutSessionComponent(
    eventsHandler: WorkoutSessionScreenEventsHandler,
    modifier: Modifier = Modifier
){
    val ioScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_LARGE
                )){
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
            onClick = { ioScope.launchIO{
                eventsHandler.addNewWorkout()
            };true },
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_NORMAL, PADDING_SMALL)
                .clickable {
                    eventsHandler.copyPreviousWorkout()
                },
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
            onClick = { ioScope.launchIO{
                eventsHandler.copyPreviousWorkout()
            };true},
            modifier = Modifier
                .weight(0.87f)
                .fillMaxWidth(0.5f)
                .padding(PADDING_NORMAL, PADDING_SMALL)
                .clickable {
                    eventsHandler.copyPreviousWorkout()
                },
        )
    }
}

/**
 * A top app-bar look alike with two arrows (<, >) and date descriptor.
 * @param currentDay [Int] input for Day element
 * @param eventsHandler [WorkoutSessionScreenEventsHandler] instance to handle following events
 * - [WorkoutSessionScreenEventsHandler.moveDayForward],
 * - [WorkoutSessionScreenEventsHandler.moveDayPrevious]
 * @param modifier view [Modifier]
 */
@Composable
fun DayInfoTopBarComponent(
    currentDay: Int,
    eventsHandler: WorkoutSessionScreenEventsHandler,
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
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
                    .clickable {
                        coroutineScope.launchIO { eventsHandler.moveDayPrevious() }
                    },
                contentDescription = "button day prev"
            )

            Text(
                text = TimeUtil.getDayFormatted(currentDay),
                modifier = Modifier
                    .weight(8f)
                    .fillMaxSize()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically),
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
                    .clickable {
                        coroutineScope.launchIO { eventsHandler.moveDayForward() }
                    },
                contentDescription = "button day next"
            )
        }

        Divider( // FIXME : Not shoeing , why ??
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .weight(0.5f),
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
    }
}

@Composable
fun WorkoutSessionScreen(
    state: PagerState,
    eventsHandler: WorkoutSessionScreenEventsHandler,
    modifier: Modifier,
){
    val coroutineScope = rememberCoroutineScope()
    val currentDay = remember { mutableStateOf(0) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DayInfoTopBarComponent(
            currentDay = currentDay.value,
            eventsHandler = eventsHandler,
            modifier = Modifier
                .weight(0.5f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer
                )
        )

        HorizontalPager(
            pageCount = Int.MAX_VALUE,
            state = state,
            modifier = Modifier
                .weight(9.5f)
                .fillMaxSize(),
        ) {day ->
            LaunchedEffect(day){
                currentDay.value = day
            }
            val workoutSessionState = eventsHandler
                .loadSessionForDay(day)
                .collectAsState(
                    initial = WorkoutSessionState.NoSession
                ) // check for recomposition leaks, since we are not using remember.

            when(workoutSessionState.value){
                is WorkoutSessionState.NoSession -> {
                    EmptyWorkoutSessionComponent(
                        eventsHandler = eventsHandler,
                        modifier = Modifier
                    )
                }
                is WorkoutSessionState.Session -> {
                    WorkoutSessionComponent(
                        workoutSession = (workoutSessionState.value as WorkoutSessionState.Session)
                            .session.first(), // for now we assume that there's only one session per day.
                        modifier = Modifier,
                        onClickAction = {workout ->
                            coroutineScope.launchIO { eventsHandler.onWorkoutSelected(workout) }
                            true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutSessionScreenPreview(){
    val coroutineScope = rememberCoroutineScope()

    val session = remember {
        WorkoutSession(
            timeMs = System.currentTimeMillis(),
            workouts = listOf(
                Workout(
                    exerciseName = "Barbell Row",
                    sets = List(10) {
                        WorkoutSet(
                            weight = 50.0,
                            reps = 10,
                            note = if (it % 2 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                        )
                    }
                ),
                Workout(
                    exerciseName = "Pull Up",
                    sets = List(4) {
                        WorkoutSet(
                            weight = 80.0,
                            reps = 10,
                            note = if (it % 3 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                        )
                    }
                ),
                Workout(
                    exerciseName = "Seated Cable Row",
                    sets = List(3) {
                        WorkoutSet(
                            weight = 63.0,
                            reps = 12,
                            note = ""
                        )
                    }
                ),
                Workout(
                    exerciseName = "Dumbbell Curl",
                    sets = List(4) {
                        WorkoutSet(
                            weight = 10.0,
                            reps = 12,
                            note = if (it % 4 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
                        )
                    }
                ),
                Workout(
                    exerciseName = "Lat Pulldown",
                    sets = List(3) {
                        WorkoutSet(
                            weight = 80.0,
                            reps = 12,
                        )
                    }
                ),
            )
        )
    }

    val pagerState = rememberPagerState(
        initialPage = TimeUtil
            .millisecondsToDays(System.currentTimeMillis())
    )

    val eventHandler = remember {
        object : WorkoutSessionScreenEventsHandler {
            override fun loadSessionForDay(day: Int): Flow<WorkoutSessionState> {
                return MutableStateFlow(
                    if (day % 2 == 0) {
                        WorkoutSessionState.NoSession
                    } else {
                        WorkoutSessionState.Session(listOf(session))
                    }
                )
            }
            override fun addNewWorkout() {}
            override fun copyPreviousWorkout() {}
            override fun moveDayPrevious() {
                coroutineScope.launch(Dispatchers.IO) {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage - 1
                    )
                }
            }
            override fun moveDayForward() {
                coroutineScope.launch(Dispatchers.IO) {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage + 1
                    )
                }
            }

            override fun onWorkoutSelected(workout: Workout) {}
        }

    }

    JimTheme {
        WorkoutSessionScreen(
            state = pagerState,
            eventsHandler = eventHandler,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        )
    }
}
