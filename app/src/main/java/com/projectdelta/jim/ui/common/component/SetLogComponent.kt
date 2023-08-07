package com.projectdelta.jim.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.projectdelta.jim.R
import com.projectdelta.jim.data.model.entity.WSTPreviewParameterProvider
import com.projectdelta.jim.data.model.entity.WorkoutSet
import com.projectdelta.jim.ui.common.conditional
import com.projectdelta.jim.ui.common.visibility
import com.projectdelta.jim.ui.common.widget.TextWithSubscript
import com.projectdelta.jim.util.callback

/**
 * Component for logging [WorkoutSet] info
 * @param modifier [Modifier]
 * @param set [WorkoutSet] data
 */
@Composable
fun SetLogComponent(
    set: WorkoutSet,
    modifier: Modifier = Modifier,
    index: Int? = null,
    hasMedal: Boolean? = null,
    onNotesClick: callback? = null,
    onMedalClick: callback? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.baseline_message_24,
            ),
            colorFilter = ColorFilter.tint(
                color = if (set.note.isNotBlank())
                    MaterialTheme.colorScheme.primary
                else
                    Color.Gray.copy(alpha = 0.7f)
            ),
            contentDescription = "message",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .visibility(set.note.isNotBlank() || onNotesClick != null)
                .conditional(onNotesClick != null) {
                    clickable(onClick = onNotesClick!!)
                },
        )
        Image(
            painter = painterResource(
                id = R.drawable.trophy_24px,
            ),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary
            ),
            contentDescription = "message",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .visibility(hasMedal != null)
                .conditional(onMedalClick != null) {
                    clickable(onClick = onMedalClick!!)
                },
        )
        TextWithSubscript(
            textNormal = index.toString(),
            textSubscript = "",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .visibility(index != null),
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
fun SetLogComponentPreview(
    @PreviewParameter(provider = WSTPreviewParameterProvider::class)
    set: WorkoutSet,
) {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            SetLogComponent(
                set = set,
                onNotesClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetLogComponentPreview1(
    @PreviewParameter(provider = WSTPreviewParameterProvider::class)
    set: WorkoutSet,
) {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            SetLogComponent(
                set = set.copy(note = "test"),
                hasMedal = true,
                index = 12,
            )
        }
    }
}

