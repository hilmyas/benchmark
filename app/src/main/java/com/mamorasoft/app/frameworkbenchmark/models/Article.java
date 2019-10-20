package com.mamorasoft.app.frameworkbenchmark.models;

import com.google.gson.annotations.SerializedName;
import com.mamorasoft.app.frameworkbenchmark.models.interfaces.IArticle;

import java.io.Serializable;

public class Article implements Serializable, IArticle {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("datetime_created")
    String datetime_created;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getDatetime_created() {
        return datetime_created;
    }

    @Override
    public void setDatetime_created(String datetime_created) {
        this.datetime_created = datetime_created;
    }
}
