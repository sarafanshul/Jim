package com.projectdelta.jim.data.state

/**
 * Manages session state for workout session
 */
sealed class SessionState<out T> {

    data object Empty : SessionState<Nothing>()

    data class Session<T>(val session: T) : SessionState<T>()
}
