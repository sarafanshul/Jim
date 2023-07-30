package com.projectdelta.jim.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.projectdelta.jim.R
import com.projectdelta.jim.data.model.entity.Workout
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.ui.theme.JimTheme

/**
 * Component for logging [WorkoutSet] info
 * @param modifier [Modifier]
 * @param set [WorkoutSet] data
 */
@Composable
fun SetLogComponent(
    modifier: Modifier,
    set: WorkoutSet,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(3f),
            painter = painterResource(
                id = R.drawable.baseline_message_24,
            ),
            colorFilter = ColorFilter.tint(
                color = if (set.note.isNotBlank()) MaterialTheme.colorScheme.primary else Color.Transparent
            ),
            contentDescription = "message"
        )
        TextWithSubscript(
            textNormal = set.weight
                .toString().padStart(5, ' '),
            textSubscript = "Kgs",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(3f),
        )
        TextWithSubscript(
            textNormal = set.reps
                .toString().padStart(2, ' '),
            textSubscript = "reps",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(3f),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetLogBoxPreview() {
    val log = Workout(
        exerciseName = "Barbell Row",
        sets = List(10) {
            WorkoutSet(
                weight = 50.0,
                reps = 10,
                note = if (it % 2 == 0) "The scope provided to your pager content allows apps to easily reference the" else ""
            )
        }
    )
    JimTheme {
        LazyColumn {
            items(log.sets) {
                SetLogComponent(
                    set = it,
                    modifier = Modifier
                )
            }
        }
    }
}
