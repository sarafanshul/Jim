package com.projectdelta.jim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.projectdelta.jim.ui.NavGraphs
import com.projectdelta.jim.ui.home.screens.HomeScreen
import com.projectdelta.jim.ui.home.HomeScreenViewModel
import com.projectdelta.jim.ui.theme.JimTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity(
) : ComponentActivity() {

    private val viewModel : HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JimTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root
                )
            }
        }
    }


}
