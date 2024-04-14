package com.example.avitofilm.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FilmPosterModelData(
    @SerializedName("docs")
    val data: List<FilmPosterData>
)

@Parcelize
data class FilmPosterData(
    @SerializedName("movieId")
    val id: String,

    @SerializedName("url")
    val url: String

): Parcelable