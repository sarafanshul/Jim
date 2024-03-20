package com.projectdelta.jim.util.paging

import androidx.paging.PagingConfig

/**
 * A Util class for all `Paging` related usecases.
 * @property defaultPagingConfig a simple [PagingConfig] for simple use-case
 */
@Suppress("MemberVisibilityCanBePrivate")
object Config {
    const val DEFAULT_STARTING_PAGE_INDEX = 0
    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_JUMPING_THRESHOLD = DEFAULT_PAGE_SIZE * 3

    val defaultPagingConfig: PagingConfig = PagingConfig(
        pageSize = DEFAULT_PAGE_SIZE,
        enablePlaceholders = false,
        jumpThreshold = DEFAULT_JUMPING_THRESHOLD
    )

    val customLocalDBPagerConfig: PagingConfig = PagingConfig(
        pageSize = 5,
        enablePlaceholders = false,
        jumpThreshold = 10
    )
}
