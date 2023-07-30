package com.projectdelta.jim.ui.home.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectdelta.jim.R
import com.projectdelta.jim.ui.common.SimpleOverflowMenu
import com.projectdelta.jim.ui.common.SimpleOverflowMenuItem
import com.projectdelta.jim.ui.home.HomeScreenViewModel
import com.projectdelta.jim.ui.home.MainTopAppBar

/**
 * Home screen composable , with a tob bar and host screen [WorkoutSessionScreen]
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            MainTopAppBar(viewModel)
        },
        content = {
            WorkoutSessionScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    )
}

