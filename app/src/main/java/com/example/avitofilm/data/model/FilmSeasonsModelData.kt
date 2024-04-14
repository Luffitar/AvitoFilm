package com.example.avitofilm.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FilmSeasonsModelData (
    @SerializedName("docs")
    val data: List<FilmSeasonsData>
)

@Parcelize
data class FilmSeasonsData(

    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("enName")
    val enName: String?,

    @SerializedName("episodesCount")
    val episodesCount: String?,


): Parcelable