package com.github.rezanejati.translator;

import com.github.rezanejati.translator.model.Trans;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public interface RetroClient {
    @GET("t")
    Observable<Trans> translate(
            @Query("client") String client,
            @Query("sc") String sc,
            @Query("v") String v,
            @Query("sl") String sl,
            @Query("tl") String tl,
            @Query("ie") String ie,
            @Query("oe") String oe,
            @Query("text") String text


    );

}
