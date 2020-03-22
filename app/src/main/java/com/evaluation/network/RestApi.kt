package com.evaluation.network

import com.evaluation.model.asset.Asset
import com.evaluation.model.search.SearchList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("search/movie?api_key=f7262134aadc3f618b7c4bfac62b4194")
    fun getSearchData(
        @Query("query") searchTerm: String,
        @Query("language") en_US: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean
    ): Single<SearchList?>

    @GET("movie/{assetId}?api_key=f7262134aadc3f618b7c4bfac62b4194")
    fun getAssetById(
        @Path("assetId") assetId: Int,
        @Query("language") en_US: String
    ): Single<Asset>
}