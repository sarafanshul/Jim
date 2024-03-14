package com.projectdelta.jim.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.projectdelta.jim.R
import com.projectdelta.jim.ui.common.widget.RepeatingIconButton
import com.projectdelta.jim.ui.theme.JimTheme
import com.projectdelta.jim.util.Constants.UI.PADDING_NORMAL
import com.projectdelta.jim.util.Constants.UI.PADDING_SMALL
import com.projectdelta.jim.util.unitCallback
import com.projectdelta.jim.util.callbackWParam

/**
 * An Horizontal number picker with two buttons for increment and decrement ,
 * - Supports input from keyboard
 * - Supports Long Press to increment/decrement values
 * >> [[-]] __ [[+]]
 *
 * **
 * Issue: Since we are passing [currentValue] raw,
 * whole [Composable] recomposes every value change,
 * but only TextEdit needs change, sad, solve this ;)
 * Resolution #1 : made [currentValue] as a [State]
 * **
 *
 */
@Composable
fun HorizontalNumberPicker(
    currentValue: State<Int>,
    onValueIncrement: unitCallback,
    onValueDecrement: unitCallback,
    onValueChange: callbackWParam<Int>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        RepeatingIconButton(
            onClick = onValueDecrement,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.remove_24),
                contentDescription = "Subtract",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
        }

        val tfv by remember(currentValue) {
            mutableStateOf(
                TextFieldValue(
                    text = currentValue.value.toString(),
                    selection = TextRange(currentValue.value.toString().length)
                ) //place cursor at the end of the text
            )
        }
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            value = tfv,
            onValueChange = {
                if (it.text.isDigitsOnly())
                    onValueChange(it.text.toIntOrNull() ?: 0)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
            ),
            singleLine = true,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .widthIn(min = 110.dp)
                .padding(horizontal = PADDING_NORMAL)
                .width(IntrinsicSize.Min)
                .indicatorLine(
                    enabled = true,
                    isError = false,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    ),
                    interactionSource = interactionSource,
                    focusedIndicatorLineThickness = 2.dp,
                    unfocusedIndicatorLineThickness = 1.dp
                )
        )
        RepeatingIconButton(
            onClick = onValueIncrement,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalNumberPickerPreview() {
    JimTheme {
        val default = remember { mutableStateOf(124) }

        HorizontalNumberPicker(
            currentValue = default,
            onValueIncrement = { default.value += 2 },
            onValueDecrement = { default.value -= 2 },
            onValueChange = { default.value = it },
            modifier = Modifier
                .padding(PADDING_SMALL)
        )
    }
}
