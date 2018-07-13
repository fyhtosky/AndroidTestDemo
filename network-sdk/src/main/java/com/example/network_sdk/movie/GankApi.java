package com.example.network_sdk.movie;

import com.example.network_sdk.movie.bean.GankResp;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface GankApi {
    /**
     *
     * @param url
     * @param
     * @param
     * @return
     */
    @GET
    Observable<GankResp> getGank(@Url String url/*, @Path("count")int count,@Path("page")int page*/);
}
