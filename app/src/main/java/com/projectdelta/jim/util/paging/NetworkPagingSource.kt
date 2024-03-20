package com.projectdelta.jim.util.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.projectdelta.jim.data.model.entity.BaseModel
import com.projectdelta.jim.util.networking.ApiResult
import com.projectdelta.jim.util.paging.Config.DEFAULT_STARTING_PAGE_INDEX
import timber.log.Timber

/**
 * Auto Paging Implementation for paging response from server,
 * refer [this](https://medium.com/nerd-for-tech/pagination-in-android-with-paging-3-retrofit-and-kotlin-flow-2c2454ff776e)
 * and [that](https://medium.com/swlh/paging3-recyclerview-pagination-made-easy-333c7dfa8797)
 * for how to implement from scratch
 *
 * - *Update 20-09 00:42* - changed params from service to a particular endpoint, because **if it works**
 * we don't have to create a new [PagingSource] for every new page endpoint.
 *
 * - *Update 20-09 17:02* - changed the implementation for [PagingSource] to support generics hence
 * we don't have to define a custom paging source for different Documents , for now it supports all
 * subclasses of [BaseModel].
 *
 * - *Update 22-09 13:01* - now [PagingSource.load] returns a [PagingSource.LoadResult.Error] when encounters a [ApiResult.NetworkError] ,
 * [ApiResult.Failure] and [ApiResult.Empty] are still grouped together and returns `nextKey = null`
 * because we can get a empty page from service i.e [ApiResult].[List.isEmpty] can be true if it's a last page
 *
 * - *Update 28-09 13:01* - **MAJOR UPDATE** [endPoint] can return [ApiResult.Success] (200) on every page with empty [List],
 * regardless it exists or not cause of spring paging controller issues this adapter will load infinite number of pages
 * mimicking a local DOS attack , now `nextKey` will be null if [ApiResult].[List.isEmpty].
 *
 * - *Update 29-09 13:01* - now supports custom filters for filtering data while fetching from endpoint. and [List.distinct] for distinct ops ,
 * due to some server side error
 *
 * - *Update 28-04 12:28* - support for jumping pages (not loading jumped pages sequentially) and default paging config : [Config.defaultPagingConfig].
 *
 * - *Update 18-03 7:24* made this more generic as per requirements
 *
 * ```
 *  Pager(
 *      config = PagingSource.defaultPagingConfig,
 * 	    pagingSourceFactory = {
 * 	        PagingSource(
 * 			    endPoint = loadMore@{ page: Int ->
 * 				    characterApi.getCharactersSortedByPower(page, reverse)
 * 			    },
 * 			    filters = filters
 * 		    )
 * 	    }
 *  )
 * ```
 *
 * @property T generic for type of document needed.
 * @param endPoint a suspend HTTP request endpoint
 * @param filters a suspend filter lambda for filtering data
 * @param defaultPageStartingIndex default start page
 *
 * @author Anshul
 */
class NetworkPagingSource<T : Any>(
    private val endPoint: suspend (Int) -> ApiResult<List<T?>>,
    private val filters: suspend (T) -> Boolean = { true },
    private val defaultPageStartingIndex: Int = DEFAULT_STARTING_PAGE_INDEX
) : PagingSource<Int, T>() {

    override val jumpingSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        Timber.d("load Called for : ${endPoint::class}")
        val pageIndex = params.key ?: defaultPageStartingIndex
        val responsePageable = endPoint(pageIndex)
        val responseData: MutableList<T> = mutableListOf()
        val nextKey: Int?
        when (responsePageable) {
            is ApiResult.Success -> {
                if (responsePageable.data.isNotEmpty()) {
                    nextKey = pageIndex + 1
                    responseData.addAll(responsePageable.data
                        .filterNotNull()
                        .filter { filters(it) }
                    )
                } else {
                    nextKey = null
                }
            }

            is ApiResult.NetworkError -> {
                return LoadResult.Error(
                    Throwable("No network connection!")
                )
            }

            else -> {
                nextKey = null
            }
        }

        return LoadResult.Page(
            data = responseData,
            prevKey = if (pageIndex == defaultPageStartingIndex) null else pageIndex,
            nextKey = nextKey
        )
    }

    /**
     * The refresh key is used for subsequent calls to NetworkPagingSource.Load after the initial load.
     */
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
