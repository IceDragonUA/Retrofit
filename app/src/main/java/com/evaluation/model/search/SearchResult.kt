package com.evaluation.model.search

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 10.03.2020
 */
data class SearchResult(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val posterPath: String
)