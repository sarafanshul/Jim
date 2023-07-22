package com.projectdelta.jim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.projectdelta.jim.data.repository.ExerciseRepository
import com.projectdelta.jim.ui.theme.JimTheme
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(
) : ComponentActivity() {

    private val viewModel : MViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JimTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Test(viewModel)
                }
            }
        }
    }
}

@Composable
fun Test(viewModel: MViewModel) {
    val list by viewModel.exercises.observeAsState(initial = listOf())

    LazyColumn(){
        items(list){ex ->
            Row {
                Text(text = ex.name)
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

}

