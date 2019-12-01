package com.mamorasoft.app.frameworkbenchmark.views;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mamorasoft.app.frameworkbenchmark.R;
import com.mamorasoft.app.frameworkbenchmark.adapters.ArticleAdapter;
import com.mamorasoft.app.frameworkbenchmark.helper.Constants;
import com.mamorasoft.app.frameworkbenchmark.helper.ImageManager;
import com.mamorasoft.app.frameworkbenchmark.helper.NotificationHelper;
import com.mamorasoft.app.frameworkbenchmark.helper.PermissionManager;
import com.mamorasoft.app.frameworkbenchmark.helper.ProgressBarSwitch;
import com.mamorasoft.app.frameworkbenchmark.helper.RecyclerViewManager;
import com.mamorasoft.app.frameworkbenchmark.helper.ToastHelper;
import com.mamorasoft.app.frameworkbenchmark.presenters.MainPresenter;
import com.mamorasoft.app.frameworkbenchmark.presenters.interfaces.IMainPresenter;
import com.mamorasoft.app.frameworkbenchmark.views.interfaces.IMainView;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements IMainView, ArticleAdapter.OnItemSelectedListener {

    RecyclerView recyclerView;
    EditText editText;
    ImageView imageView;
    Button buttonBrowse, buttonSend, buttonTakePhoto;
    Bitmap bitmap;
    ProgressBar progressBar;
    ArticleAdapter adapter;

    File fileKTP;

    IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!PermissionManager.isRequiredPermissionAllGranted(this)) {
            PermissionManager.requestPermission(this);
        } else {
            init();
            getData();

            HashMap<String, Object> notifOpts = new HashMap<>();
            notifOpts.put(NotificationHelper.Companion.getTITLE(), "Title");
            notifOpts.put(NotificationHelper.Companion.getCONTENT(), "Content");
            notifOpts.put(NotificationHelper.Companion.getFULL_CONTENT(), "Full content");
            notifOpts.put(NotificationHelper.Companion.getNOTIF_ID(), 1234);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationHelper.Companion.showNotification(this, notifOpts, pendingIntent);
        }

    }

    private void init() {
        mainPresenter = new MainPresenter(this);
        adapter = new ArticleAdapter(mainPresenter.getArticles());
        adapter.setOnItemSelectedListener(this);

        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.imageView);
        buttonBrowse = findViewById(R.id.buttonBrowse);
        buttonSend = findViewById(R.id.buttonSend);

        RecyclerViewManager.setLayoutLinear(recyclerView, this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.setFileUri(ImageManager.dispatchTakePictureIntent(MainActivity.this));
            }
        });

        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constants.CODE_BROWSE);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompleted()) {
                    sendData();
                }

            }
        });
    }

    private void getData() {
        showProgressBar(true);

        mainPresenter.getData();
    }

    private void sendData() {
        showProgressBar(true);

        mainPresenter.sendData(editText.getText().toString());
    }

    public void showProgressBar(boolean show) {
        ProgressBarSwitch.showProgressBar(this, progressBar, show);
    }

    public void showToast(String message, int duration) {
        ToastHelper.showToast(this, message, duration);
    }

    private boolean isCompleted() {
        boolean is = true;

        if (editText.getText().toString().equalsIgnoreCase("")) {
            is = false;
            Toast.makeText(this, "Silakan isi tulisan", Toast.LENGTH_SHORT).show();
            editText.requestFocus();
        }

        return is;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.CODE_REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    mainPresenter.onTakePhotoSuccess();
                }
                break;
            case Constants.CODE_BROWSE:
                if (resultCode == RESULT_OK) {
                    mainPresenter.onBrowseImageSuccess(data.getData());
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.CODE_REQUEST_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && (
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    //thanks
                    init();
                } else {
                    Toast.makeText(this, "Izinkan aplikasi mengakses penyimpanan Anda", Toast.LENGTH_LONG).show();
                    PermissionManager.requestPermission(this);
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onSendSuccess(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        getData();
        showProgressBar(false);
    }

    @Override
    public void onSendError(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        Log.e(getResources().getString(R.string.app_name), message);
        showProgressBar(false);
    }

    @Override
    public void onGetDataSuccess(String message) {
        adapter.notifyDataSetChanged();
        showToast(message, Toast.LENGTH_SHORT);
        showProgressBar(false);
    }

    @Override
    public void onGetDataError(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        Log.e(getResources().getString(R.string.app_name), message);
        showProgressBar(false);
    }

    @Override
    public void onGetImageSuccess() {
        try {
            bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(
                    getContentResolver(), mainPresenter.getFileUri()), 300, 300, false);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            showToast("Error: " + e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onItemSelected(int pos) {

    }
}
