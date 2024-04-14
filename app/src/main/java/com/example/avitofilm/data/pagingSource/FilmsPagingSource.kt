package com.example.avitofilm.data.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.avitofilm.data.model.FilmsData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants.LIMIT

const val NETWORK_PAGE_SIZE = 5
class FilmsPagingSource (
    private val api: FilmsApi,
    private val query: String,
    private val year: Array<Int>,
    private val countries: Array<String>,
    private val ageRating: Array<Int>,
) : PagingSource<Int, FilmsData>(){
    override fun getRefreshKey(state: PagingState<Int, FilmsData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmsData> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)

            val response = if (query.isNotEmpty()) {
                api.getFilms(query = query, limit = LIMIT, page = page)
            }
            else {
                api.getFilterFilms(year = year, countries = countries,
                    ageRating = ageRating, limit = LIMIT, page = page)
            }
            val films = response.data

            val nextKey = if (films.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(films, prevKey, nextKey)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}