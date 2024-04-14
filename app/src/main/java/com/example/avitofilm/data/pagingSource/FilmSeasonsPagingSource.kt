package com.example.avitofilm.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.avitofilm.data.model.FilmSeasonsData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants

class FilmSeasonsPagingSource(
    private val api: FilmsApi,
    private val movieId: Array<String>
) : PagingSource<Int, FilmSeasonsData>(){
    override fun getRefreshKey(state: PagingState<Int, FilmSeasonsData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmSeasonsData> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)

            val response = api.getSeasonsReview(movieId = movieId, limit = Constants.LIMIT, page = page)
            val seasons = response.data

            val nextKey = if (seasons.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(seasons, prevKey, nextKey)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}