package com.example.avitofilm.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.avitofilm.data.model.FilmReviewData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants.LIMIT

class FilmReviewPagingSource(
    private val api: FilmsApi,
    private val movieId: Array<String>
) : PagingSource<Int, FilmReviewData>() {
    override fun getRefreshKey(state: PagingState<Int, FilmReviewData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmReviewData> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)

            val response = api.getFilmReview(movieId = movieId, limit = LIMIT, page = page)
            val reviews = response.data

            val nextKey = if (reviews.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(reviews, prevKey, nextKey)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}