package com.projectdelta.jim.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.projectdelta.jim.util.paging.Config.DEFAULT_STARTING_PAGE_INDEX
import timber.log.Timber

/**
 * Local [PagingSource] to handle custom DB loads
 *
 * **This is custom made for the usecase of workout sessions**,
 * we need a infinite stream of data, some days there's no session,
 * next day there can be a session. Hence we need a infinite stream
 *
 * How to tackle this:
 *  - we pool [loadData] with a key (day-from-epoch)
 *  - 2 possibilities for return null OR some valid data.
 *  - in any case we increment next key.
 *
 * @property T generic for type of document needed.
 * @param loadData this function takes a [Int] (Index) and returns respective data.
 * @param defaultPageStartingIndex default start page
 */
class LocalPagingSource<T: Any>(
    private val loadData: suspend (Int) -> T,
    private val defaultPageStartingIndex: Int = DEFAULT_STARTING_PAGE_INDEX,
) : PagingSource<Int, T>() {

    override val jumpingSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageIndex = params.key ?: defaultPageStartingIndex

        val responsePageable = loadData(pageIndex)
        val responseData = mutableListOf(responsePageable)
        val nextKey = pageIndex + 1

        return LoadResult.Page(
            data = responseData,
            prevKey = if(pageIndex == defaultPageStartingIndex) null else pageIndex,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
