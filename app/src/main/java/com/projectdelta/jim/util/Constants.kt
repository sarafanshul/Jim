package com.projectdelta.jim.util

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingConfig

@Suppress("MemberVisibilityCanBePrivate", "unused")
object Constants {

    /**
     * DB_Table Constants
     */
    object Table {
        const val EXERCISE_TABLE = "exercise_table"
        const val WORKOUT_SESSION_TABLE = "workout_session_table"
        const val DEFAULT_VALUE_STR = "404_null"
    }

    /**
     * Database Constants
     */
    object Database {
        const val NAME = "jim_database"
        const val VERSION = 1
    }

    /**
     * Paging Constants
     */
    object PagingSource {
        const val DEFAULT_STARTING_PAGE_INDEX = 0
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_JUMPING_THRESHOLD = DEFAULT_PAGE_SIZE * 3

        val defaultPagingConfig: PagingConfig = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            jumpThreshold = DEFAULT_JUMPING_THRESHOLD
        )

    }

    object UI {
        val TEXT_SMALL = 10.sp
        val TEXT_SMALL_PLUS = 13.sp
        val TEXT_LARGE = 26.sp
        val TEXT_NOT_THAT_LARGE = 22.sp
        val TEXT_MEDIUM = 18.sp

        val PADDING_NORMAL = 10.dp
        val PADDING_SMALL = 4.dp
        val ROUND_RADIUS_NORMAL = 6.dp
        val ELEVATION_NORMAL = 6.dp

    }

    object StringRes{
        const val NO_WORKOUT_LOG = "Workout Log Empty"
    }

}
