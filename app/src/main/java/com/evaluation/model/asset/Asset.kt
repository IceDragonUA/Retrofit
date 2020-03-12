package com.evaluation.model.asset

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 10.03.2020
 */
data class Asset(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("backdrop_path")
    val posterPath: String
)