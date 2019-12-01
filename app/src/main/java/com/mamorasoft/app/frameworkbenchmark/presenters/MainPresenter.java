package com.mamorasoft.app.frameworkbenchmark.presenters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.mamorasoft.app.frameworkbenchmark.R;
import com.mamorasoft.app.frameworkbenchmark.adapters.ArticleAdapter;
import com.mamorasoft.app.frameworkbenchmark.helper.ApiClient;
import com.mamorasoft.app.frameworkbenchmark.helper.FileManager;
import com.mamorasoft.app.frameworkbenchmark.helper.ImageManager;
import com.mamorasoft.app.frameworkbenchmark.interfaces.ArticleApiInterface;
import com.mamorasoft.app.frameworkbenchmark.models.Article;
import com.mamorasoft.app.frameworkbenchmark.presenters.interfaces.IMainPresenter;
import com.mamorasoft.app.frameworkbenchmark.responses.ResponseArticle;
import com.mamorasoft.app.frameworkbenchmark.views.interfaces.IMainView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements IMainPresenter {

    IMainView mainView;
    Uri fileUri;
    File fileKTP;
    ArticleApiInterface mApiInterface;
    ArrayList<Article> articles;
    String image_type;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        mApiInterface = ApiClient.getClient().create(ArticleApiInterface.class);
        articles = new ArrayList<>();

    }

    @Override
    public void getData() {
        Call<ResponseArticle> call = mApiInterface.getListArticle();
        Log.e(((Activity) mainView).getResources().getString(R.string.app_name), call.request().url() + "");
        call.enqueue(new Callback<ResponseArticle>() {
            @Override
            public void onResponse(Call<ResponseArticle> call, Response<ResponseArticle> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        articles.clear();
                        articles.addAll(response.body().getArticles());
                        mainView.onGetDataSuccess(response.body().getMessage());
                    } else {
                        mainView.onGetDataError(response.body().getMessage());
                    }

                } else {
                    mainView.onGetDataError("Gagal memproses respon server");
                }
            }

            @Override
            public void onFailure(Call<ResponseArticle> call, Throwable t) {
                mainView.onGetDataError("Error" + t.toString());
            }
        });
    }

    @Override
    public void sendData(String name) {
        Call<ResponseArticle> call;
        if (fileUri != null) {
            //creating a file
            Log.i(((Activity) mainView).getResources().getString(R.string.app_name), "fileUri.getPath() " + fileUri.getPath());
            Log.i(((Activity) mainView).getResources().getString(R.string.app_name), "fileKTP.getName() " + fileKTP.getName());
            Log.i(((Activity) mainView).getResources().getString(R.string.app_name), "getMimeType(fileUri.getPath()) " + ImageManager.getMimeType(fileUri.getPath()) + "");

            try {
                Bitmap bitmap = BitmapFactory.decodeFile(fileKTP.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, new FileOutputStream(fileKTP));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }

            call = mApiInterface.sendCriticSuggest(
                    MultipartBody.Part.createFormData("image", fileKTP.getName(), RequestBody.create(MediaType.parse(image_type), fileKTP)),
                    RequestBody.create(MediaType.parse("text/plain"), name));
        } else {
            call = mApiInterface.sendCriticSuggestNoImage(
                    RequestBody.create(MediaType.parse("text/plain"), name));
        }
        Log.e(((Activity) mainView).getResources().getString(R.string.app_name), call.request().url() + "");
        call.enqueue(new Callback<ResponseArticle>() {
            @Override
            public void onResponse(Call<ResponseArticle> call, Response<ResponseArticle> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        mainView.onSendSuccess(response.body().getMessage());
                    } else {
                        mainView.onSendError(response.body().getMessage());
                    }

                } else {
                    mainView.onSendError("Gagal memproses respon server");
                }
            }

            @Override
            public void onFailure(Call<ResponseArticle> call, Throwable t) {
                mainView.onSendError("Error" + t.toString());
            }
        });
    }

    @Override
    public ArrayList<Article> getArticles() {
        return articles;
    }

    @Override
    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public void onTakePhotoSuccess() {
        fileKTP = new File(fileUri.getPath());
        image_type = ImageManager.getMimeType(fileUri.getPath());

        mainView.onGetImageSuccess();
    }

    @Override
    public void onBrowseImageSuccess(Uri fileUri) {
        this.fileUri = fileUri;
        image_type = ((Activity) mainView).getContentResolver().getType(fileUri);
        fileKTP = new File(FileManager.getRealImagePathFromURI((Activity) mainView, fileUri));

        mainView.onGetImageSuccess();
    }

    @Override
    public Uri getFileUri() {
        return fileUri;
    }
}
