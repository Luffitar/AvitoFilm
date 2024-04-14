package com.example.avitofilm.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FilmPersonsModelData (
    @SerializedName("docs")
    val data: List<FilmPersonsData>
)

@Parcelize
data class FilmPersonsData(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("enName")
    val enName: String?,

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("sex")
    val sex: String?,

    @SerializedName("age")
    val age: Int?

): Parcelable