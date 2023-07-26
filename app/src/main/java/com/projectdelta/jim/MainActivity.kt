package com.projectdelta.jim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.projectdelta.jim.data.model.Exercise
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.ui.screen.home.HomeScreen
import com.projectdelta.jim.ui.screen.home.HomeScreenViewModel
import com.projectdelta.jim.ui.theme.JimTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

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
