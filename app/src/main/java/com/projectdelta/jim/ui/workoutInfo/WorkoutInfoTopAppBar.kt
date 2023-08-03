package com.projectdelta.jim.ui.workoutInfo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.projectdelta.jim.R
import com.projectdelta.jim.ui.common.widget.SimpleOverflowMenu
import com.projectdelta.jim.ui.common.widget.SimpleOverflowMenuItem
import com.projectdelta.jim.util.system.lang.chop

@Composable
fun WorkoutInfoTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Exercise Name"
                    .chop(15),
                style = MaterialTheme.typography.titleMedium
            ) // ellipsize it
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },

        actions = {
            IconButton( // for timer.
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.timer_24px),
                    contentDescription = "Timer",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton( // for Records.
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trophy_24px),
                    contentDescription = "Records",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton( // for Info.
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info_24px),
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            SimpleOverflowMenu {
                SimpleOverflowMenuItem( // RM calculator
                    clickAction = { /*TODO*/ },
                    text = "1RM Calculator",
                    imageResource = R.drawable.azm_24px,
                )
                SimpleOverflowMenuItem( // Set calculator
                    clickAction = { /*TODO*/ },
                    text = "Set Calculator",
                    imageResource = R.drawable.calculate_24px,
                )
                SimpleOverflowMenuItem( // Plate calculator
                    clickAction = { /*TODO*/ },
                    text = "Plate Calculator",
                    imageResource = R.drawable.exercise_24px,
                )
            }
        }
    )
}

@Preview
@Composable
fun WorkoutInfoTopAppBarPreview() {

}
