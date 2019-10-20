package com.mamorasoft.app.frameworkbenchmark.views.interfaces;

public interface IMainView {
    void onSendSuccess(String message);
    void onSendError(String message);
    void onGetDataSuccess(String message);
    void onGetDataError(String message);
    void onGetImageSuccess();
}
