package com.evaluation.model.search

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 10.03.2020
 */
data class SearchList(
    @SerializedName("results")
    val searchResultList: List<SearchResult>
)