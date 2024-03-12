package com.projectdelta.jim.util.component

import androidx.compose.runtime.Composable


/**
 * Compose recomposes only the "nearest" scope. A scope is non-inline Composable function that returns Unit.
 * Implementation of functions like Row, Column, Box, and Layout in Compose.
 * These functions are marked as inline, which means they don't create separate scopes
 * but pass their call block directly to the call site.
 * This causes un-necessary recompositions.
 */
@Composable
fun NonInlineUIWrapper(content: @Composable () -> Unit) {
    content()
}
