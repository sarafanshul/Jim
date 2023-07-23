package com.projectdelta.jim.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) =
    launch(Dispatchers.IO, block = block)
