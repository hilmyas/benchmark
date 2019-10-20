package com.mamorasoft.app.frameworkbenchmark.helper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().
                    readTimeout(30, TimeUnit.SECONDS).
                    connectTimeout(30, TimeUnit.SECONDS).
                    callTimeout(30, TimeUnit.SECONDS).
                    writeTimeout(30, TimeUnit.SECONDS).
                    build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL + Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
