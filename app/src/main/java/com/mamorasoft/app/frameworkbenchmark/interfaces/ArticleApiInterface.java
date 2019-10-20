package com.mamorasoft.app.frameworkbenchmark.interfaces;

import com.mamorasoft.app.frameworkbenchmark.responses.ResponseArticle;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ArticleApiInterface {
    @Multipart
    @POST("post-article")
    Call<ResponseArticle> sendCriticSuggest(@Part MultipartBody.Part image,
                                            @Part("name") RequestBody critic_suggestion);

    @Multipart
    @POST("post-article")
    Call<ResponseArticle> sendCriticSuggestNoImage(@Part("name") RequestBody critic_suggestion);

    @GET("articles")
    Call<ResponseArticle> getListArticle();
}
