package com.example.avitofilm.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.avitofilm.data.model.FilmPosterData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants.LIMIT

class FilmPosterPagingSource(
    private val api: FilmsApi,
    private val movieId: Array<String>
) : PagingSource<Int, FilmPosterData>(){
    override fun getRefreshKey(state: PagingState<Int, FilmPosterData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmPosterData> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)

            val response = api.getFilmPosters(movieId = movieId, limit = LIMIT, page = page)
            val posters = response.data

            val nextKey = if (posters.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(posters, prevKey, nextKey)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}