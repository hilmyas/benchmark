package com.mamorasoft.app.frameworkbenchmark.presenters.interfaces;

import android.net.Uri;

import com.mamorasoft.app.frameworkbenchmark.models.Article;

import java.util.ArrayList;

public interface IMainPresenter {
    void getData();
    void sendData(String name);
    ArrayList<Article> getArticles();
    void setFileUri(Uri fileUri);
    void onTakePhotoSuccess();
    void onBrowseImageSuccess(Uri fileUri);
    Uri getFileUri();
}
