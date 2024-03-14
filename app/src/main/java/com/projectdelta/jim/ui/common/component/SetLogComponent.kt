package com.projectdelta.jim.ui.common.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.unitCallback
import com.projectdelta.jim.util.component.NonInlineUIWrapper
import com.projectdelta.jim.util.system.lang.numberFormatLocale

/**
 * Component for logging [WorkoutSet] info
 * @param modifier [Modifier]
 * @param setWeight [WorkoutSet.weight] in closure
 * @param setNote [WorkoutSet.note] in closure
 * @param setReps [WorkoutSet.reps] in closure
 */
@Composable
fun SetLogComponent(
    setWeight: () -> Double,
    setNote: () -> String,
    setReps: () -> Int,
    modifier: Modifier = Modifier,
    index: Int? = null,
    hasMedal: Boolean? = null,
    onNotesClick: unitCallback? = null,
    onMedalClick: unitCallback? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        NonInlineUIWrapper {
            Image(
                painter = painterResource(
                    id = R.drawable.baseline_message_24,
                ),
                colorFilter = ColorFilter.tint(
                    color = if (setNote().isNotBlank())
                        MaterialTheme.colorScheme.primary
                    else
                        Color(0xAABDC3C7)
                ),
                contentDescription = "message",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .visibility(setNote().isNotBlank() || onNotesClick != null)
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
                textNormal = setWeight()
                    .numberFormatLocale(formatter = "%,.1f")
                    .padStart(5, ' '),
                textSubscript = "Kgs",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(3f),
            )
            TextWithSubscript(
                textNormal = setReps()
                    .toString().padStart(2, ' '),
                textSubscript = "reps",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(3f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetLogComponentPreview(
    @PreviewParameter(provider = WSTPreviewParameterProvider::class)
    set: WorkoutSet,
) {
    JimTheme {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            SetLogComponent(
                setWeight = { set.weight },
                setReps = { set.reps },
                setNote = { set.note },
                onNotesClick = {}
            )
        }
    }
}

@Preview(
    name = "Dark Selected",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SetLogComponentPreview1(
    @PreviewParameter(provider = WSTPreviewParameterProvider::class)
    set: WorkoutSet,
) {
    JimTheme {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                SetLogComponent(
                    setWeight = { set.weight },
                    setReps = { set.reps },
                    setNote = { "test" },
                    hasMedal = true,
                    index = 12,
                )
            }
        }
    }
}

