package com.example.avitofilm.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.avitofilm.data.model.FilmPersonsData
import com.example.avitofilm.data.model.FilmPosterData
import com.example.avitofilm.data.model.FilmReviewData
import com.example.avitofilm.data.model.FilmSeasonsData
import com.example.avitofilm.data.model.FilmsData
import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.data.pagingSource.FilmPersonsPagingSource
import com.example.avitofilm.data.pagingSource.FilmPosterPagingSource
import com.example.avitofilm.data.pagingSource.FilmReviewPagingSource
import com.example.avitofilm.data.pagingSource.FilmSeasonsPagingSource
import com.example.avitofilm.data.pagingSource.FilmsPagingSource
import com.example.avitofilm.data.pagingSource.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmsRepository @Inject constructor(private val api: FilmsApi){

    private var query: String = ""
        set(value) {
            field = value
//            updatePagingSource()
        }

    private var movieIdPosters: Array<String> = emptyArray()
        set(value) {
            field = value
            posterPagingSource = FilmPosterPagingSource(api = api, movieId = field)
        }

    private var posterPagingSource: FilmPosterPagingSource =
        FilmPosterPagingSource(api = api, movieId = movieIdPosters)

    private var movieIdPersons: Array<String> = emptyArray()
        set(value) {
            field = value
            personsPagingSource = FilmPersonsPagingSource(api = api, movieId = field)
        }

    private var personsPagingSource: FilmPersonsPagingSource =
        FilmPersonsPagingSource(api = api, movieId = movieIdPersons)

    private var movieIdReviews: Array<String> = emptyArray()
        set(value) {
            field = value
            reviewsPagingSource = FilmReviewPagingSource(api = api, movieId = field)
        }

    private var reviewsPagingSource: FilmReviewPagingSource =
        FilmReviewPagingSource(api = api, movieId = movieIdReviews)

    private var movieIdSeasons: Array<String> = emptyArray()
        set(value) {
            field = value
            seasonsPagingSource = FilmSeasonsPagingSource(api = api, movieId = field)
        }

    private var seasonsPagingSource: FilmSeasonsPagingSource =
        FilmSeasonsPagingSource(api = api, movieId = movieIdSeasons)

    private var year: Array<Int> = emptyArray()
        set(value) {
            field = value
//            updatePagingSource()
        }
    private var countries: Array<String> = emptyArray()
        set(value) {
            field = value
//            updatePagingSource()
        }
    private var ageRating: Array<Int> = emptyArray()
        set(value) {
            field = value
//            updatePagingSource()
        }

    private var pagingSource: FilmsPagingSource = FilmsPagingSource(query = query, api = api,
        year = year, countries = countries, ageRating = ageRating)

    private fun updatePagingSource() {
        pagingSource = FilmsPagingSource( query = query, api = api, year = year, countries = countries,
            ageRating = ageRating)
    }

    fun getFilmsFlow() : Flow<PagingData<FilmsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FilmsPagingSource(query = query, api = api,
                    year = year, countries = countries, ageRating = ageRating).also{ pagingSource = it }
            }
        ).flow
    }

    fun getPosterFilmsFlow() : Flow<PagingData<FilmPosterData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                posterPagingSource
            }
        ).flow
    }

    fun getPersonsFilmsFlow() : Flow<PagingData<FilmPersonsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                personsPagingSource
            }
        ).flow
    }

    fun getReviewsFilmsFlow() : Flow<PagingData<FilmReviewData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                reviewsPagingSource
            }
        ).flow
    }

    fun getSeasonsFilmsFlow() : Flow<PagingData<FilmSeasonsData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                seasonsPagingSource
            }
        ).flow
    }

    fun searchFilms(query: String, year: Array<Int>, countries: Array<String>,
                    ageRating: Array<Int>){
        this.query = query
        this.year = year
        this.countries = countries
        this.ageRating = ageRating
        pagingSource.invalidate()
    }

    fun searchPosterFilms(movieId: Array<String>){
        posterPagingSource.invalidate()
        this.movieIdPosters = movieId
    }

    fun searchPersonsFilms(movieId: Array<String>){
        personsPagingSource.invalidate()
        this.movieIdPersons = movieId
    }

    fun searchReviewsFilms(movieId: Array<String>){
        reviewsPagingSource.invalidate()
        this.movieIdReviews = movieId
    }

    fun searchSeasonsFilms(movieId: Array<String>){
        seasonsPagingSource.invalidate()
        this.movieIdSeasons = movieId
    }

}