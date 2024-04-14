package com.example.avitofilm.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.avitofilm.data.model.FilmPersonsData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants.LIMIT

class FilmPersonsPagingSource(
    private val api: FilmsApi,
    private val movieId: Array<String>
) : PagingSource<Int, FilmPersonsData>(){
    override fun getRefreshKey(state: PagingState<Int, FilmPersonsData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmPersonsData> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize: Int = params.loadSize.coerceAtMost(NETWORK_PAGE_SIZE)

            val response = api.getFilmPersons(movieId = movieId, limit = LIMIT,
                page = page, movieEnProfession = arrayOf("actor"))
            val persons = response.data

            val nextKey = if (persons.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(persons, prevKey, nextKey)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}