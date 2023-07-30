package com.projectdelta.jim.data.state

/**
 * Manages session state for workout session
 */
sealed class SessionState<T> {

    object Empty : SessionState<Unit>()

    data class Session<T>(val session: T) : SessionState<T>()
}
