package com.evaluation.network;

import com.evaluation.model.asset.Asset;
import com.evaluation.model.search.SearchList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {

    @GET("search/movie?api_key=f7262134aadc3f618b7c4bfac62b4194")
    Single<SearchList> getSearchData(
            @Query("query") String searchTerm,
            @Query("language") String en_US,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult

    );

    @GET("movie/{assetId}?api_key=f7262134aadc3f618b7c4bfac62b4194")
    Single<Asset> getAssetById(
            @Path("assetId") int assetId,
            @Query("language") String en_US
    );
}


