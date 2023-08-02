package com.projectdelta.jim.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.projectdelta.jim.util.callback

/**
 * Wrapper around [DropdownMenu] for convenience
 */
@Composable
fun SimpleOverflowMenu(
    accentColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable ColumnScope.() -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(
        onClick = { showMenu = !showMenu }
    ) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "more",
            tint = accentColor
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        content = content
    )
}

/**
 * Wrapper around [DropdownMenuItem] for convenience
 */
@Composable
fun SimpleOverflowMenuItem(
    clickAction: callback,
    text: String,
    imageVector: ImageVector,
    accentColor: Color = MaterialTheme.colorScheme.primary
){
    DropdownMenuItem(
        onClick = clickAction,
        text = {
            Text(text = text)
        },
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = text,
                tint = accentColor,
            )
        }
    )
}

/**
 * Wrapper around [DropdownMenuItem] for convenience
 */
@Composable
fun SimpleOverflowMenuItem(
    clickAction: callback,
    text: String,
    @DrawableRes imageResource: Int,
    accentColor: Color = MaterialTheme.colorScheme.primary
){
    DropdownMenuItem(
        onClick = clickAction,
        text = {
            Text(text = text)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = imageResource),
                contentDescription = text,
                tint = accentColor,
            )
        }
    )
}

