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
import com.projectdelta.jim.ui.theme.JimTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(
) : ComponentActivity() {

    private val viewModel : MViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val exercises = viewModel.exercisesPager.collectAsLazyPagingItems()

            JimTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun Test(exercises : LazyPagingItems<Exercise>) {
    Timber.d("item count : ${exercises.itemCount}")
    when (exercises.loadState.refresh){
        LoadState.Loading -> {

        }
        is LoadState.Error -> {

        }
        is LoadState.NotLoading -> {
            LazyColumn{
                items(
                    count = exercises.itemCount,
                    key = exercises.itemKey { it.id },
                ) { index ->
                    val exercise = exercises[index] ?: return@items
                    Timber.d("index : $index, ex : ${exercise.name}")
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = exercise.name)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JimTheme {
    }
}

@HiltViewModel
class MViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    val exercises = repository.getAllExercises().asLiveData()

    val exercisesPager = repository.getAllExercisesPaged()

}

