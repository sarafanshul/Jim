package com.projectdelta.jim.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectdelta.jim.R
import com.projectdelta.jim.ui.common.widget.SimpleOverflowMenu
import com.projectdelta.jim.ui.common.widget.SimpleOverflowMenuItem
import com.projectdelta.jim.ui.home.events.HomeScreenEvent


@Composable
fun HomeTopAppBar(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeScreenState.collectAsState()
    TopAppBar(
        title = {
            Text(text = "Jim")
        },
        navigationIcon = {
            IconButton(onClick = {
                viewModel.handleEvent(HomeScreenEvent.NavigationAppIconClickEvent)
            }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ecg_heart_24,
                    ), contentDescription = "Home Screen",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },

        actions = {
            IconButton(onClick = {
                viewModel.handleEvent(HomeScreenEvent.LaunchCalendarEvent())
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_month_24),
                    contentDescription = "Calendar for history",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = {
                viewModel.handleEvent(HomeScreenEvent.CreateNewWorkoutEvent)
            }) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add New Workout",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            SimpleOverflowMenu {
                SimpleOverflowMenuItem(
                    clickAction = {
                        viewModel.handleEvent(HomeScreenEvent.LaunchSettingScreenEvent)
                    },
                    text = "Settings",
                    imageVector = Icons.Outlined.Settings,
                )
                SimpleOverflowMenuItem(
                    // tricky impl, left for now.
                    clickAction = { /*TODO*/ },
                    text = "Copy workout",
                    imageResource = R.drawable.baseline_content_copy_24,
                )
                SimpleOverflowMenuItem(
                    clickAction = {
                        viewModel.handleEvent(HomeScreenEvent.ShareWorkoutEvent(uiState.currentDay))
                    },
                    text = "Share Workout",
                    imageResource = R.drawable.share_24,
                )
                SimpleOverflowMenuItem(
                    clickAction = {
                        viewModel.handleEvent(HomeScreenEvent.LaunchBodyTrackScreenEvent)
                    },
                    text = "Body Tracker",
                    imageResource = R.drawable.elevator_24,
                )
                SimpleOverflowMenuItem(
                    clickAction = {
                        viewModel.handleEvent(HomeScreenEvent.LaunchAnalysisScreenEvent)
                    },
                    text = "Analysis",
                    imageResource = R.drawable.analytics_24,
                )
            }
        }
    )
}
