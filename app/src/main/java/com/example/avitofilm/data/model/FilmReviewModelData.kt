package com.example.avitofilm.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FilmReviewModelData (
    @SerializedName("docs")
    val data: List<FilmReviewData>
)

@Parcelize
data class FilmReviewData(
    @SerializedName("id")
    val id: String,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("review")
    val review: String?,

    @SerializedName("date")
    val date: String?

): Parcelable