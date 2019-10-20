package com.mamorasoft.app.frameworkbenchmark.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "beyond_childhood";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "id";
    public static final String KEY_ACCESS_TOKEN= "access_token";
    public static final String KEY_FCM_BROADCAST_TOKEN= "fcm_broadcast_token";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id, String access_token){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_ACCESS_TOKEN, pref.getString(KEY_ACCESS_TOKEN, null));
        // return user
        return user;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        editor.putString(KEY_FCM_BROADCAST_TOKEN, token);
        editor.commit();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_FCM_BROADCAST_TOKEN, pref.getString(KEY_FCM_BROADCAST_TOKEN, null));
        return user.get(KEY_FCM_BROADCAST_TOKEN);
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
