package com.projectdelta.jim.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.projectdelta.jim.R

/**
 * Home screen composable , with a tob bar and host screen [WorkoutSessionScreen]
 */
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Jim")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_message_24
                            ),
                            contentDescription = ""
                        )
                    }
                }
            )
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

