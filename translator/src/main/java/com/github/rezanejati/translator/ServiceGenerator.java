package com.github.rezanejati.translator;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Reza.nejati on 3/25/2018.
 */

public class ServiceGenerator {
    static String URL = "http://translate.google.com/translate_a/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        Interceptor header = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/x.api.v1+json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };


        Retrofit retrofit = builder.client(httpClient
                .addInterceptor(header)
                .build())
                .build();

        return retrofit.create(serviceClass);
    }


}

