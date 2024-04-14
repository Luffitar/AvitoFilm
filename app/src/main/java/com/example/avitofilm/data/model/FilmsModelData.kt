package com.example.avitofilm.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class FilmsModelData(
    @SerializedName("docs")
    val data: List<FilmsData>
)

@Parcelize
data class FilmsData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("year")
    val year: Int,

    @SerializedName("countries")
    val countries: List<Country>,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("description")
    val description: String?,

    @SerializedName("poster")
    val poster: Posters,

    @SerializedName("rating")
    val rating: Rating?
): Parcelable

@Parcelize
data class Posters(
    @SerializedName("previewUrl")
    val previewUrl: String?
): Parcelable

@Parcelize
data class Country(
    @SerializedName("name")
    val name: String
): Parcelable

@Parcelize
data class Genre(
    @SerializedName("name")
    val name: String
): Parcelable

@Parcelize
data class Rating(
    @SerializedName("kp")
    val kp: Double?,

    @SerializedName("imdb")
    val imdb: Double?,

    @SerializedName("filmCritics")
    val filmCritics: Double?,

    @SerializedName("russianFilmCritics")
    val russianFilmCritics: Double?,

): Parcelable