package com.mamorasoft.app.frameworkbenchmark.responses;

import com.google.gson.annotations.SerializedName;
import com.mamorasoft.app.frameworkbenchmark.models.Article;

import java.util.ArrayList;

public class ResponseArticle {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("articles")
    ArrayList<Article> articles;
//    @SerializedName("banner_url")
//    String banner_url;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
