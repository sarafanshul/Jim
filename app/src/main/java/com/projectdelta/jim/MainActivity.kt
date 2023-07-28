package com.projectdelta.jim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.projectdelta.jim.ui.screen.home.HomeScreen
import com.projectdelta.jim.ui.screen.home.HomeScreenViewModel
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.MockDebugData
import com.projectdelta.jim.util.launchIO
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity(
) : ComponentActivity() {

    private val viewModel : HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JimTheme {
                HomeScreen(
                    viewModel = viewModel,
                )
            }
        }
    }


}
