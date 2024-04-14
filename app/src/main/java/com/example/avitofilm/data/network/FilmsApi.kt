package com.example.avitofilm.data.network

import com.example.avitofilm.data.model.FilmPersonsModelData
import com.example.avitofilm.data.model.FilmPosterModelData
import com.example.avitofilm.data.model.FilmReviewModelData
import com.example.avitofilm.data.model.FilmSeasonsModelData
import com.example.avitofilm.data.model.FilmsModelData
import com.example.avitofilm.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FilmsApi {

    @GET("movie/search")
    suspend fun getFilms(
        @Query("query") query: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmsModelData

    @GET("movie")
    suspend fun getFilterFilms(
        @Query("year") year: Array<Int>,
        @Query("countries.name") countries: Array<String>,
        @Query("ageRating") ageRating: Array<Int>,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmsModelData

    @GET("image")
    suspend fun getFilmPosters(
        @Query("movieId") movieId: Array<String>,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmPosterModelData

    @GET("person")
    suspend fun getFilmPersons(
        @Query("movies.id") movieId: Array<String>,
        @Query("movies.enProfession") movieEnProfession: Array<String>,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmPersonsModelData

    @GET("review")
    suspend fun getFilmReview(
        @Query("movieId") movieId: Array<String>,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmReviewModelData

    @GET("season")
    suspend fun getSeasonsReview(
        @Query("movieId") movieId: Array<String>,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): FilmSeasonsModelData
}