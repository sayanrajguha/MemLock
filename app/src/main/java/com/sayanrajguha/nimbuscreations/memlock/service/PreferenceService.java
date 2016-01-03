package com.sayanrajguha.nimbuscreations.memlock.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.sayanrajguha.nimbuscreations.memlock.constants.AppConstants;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public class PreferenceService {

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.APP_PREF_FILE_NAME,Context.MODE_PRIVATE);

        if(null != sharedPreferences) {
            sharedPreferences.edit().putString(preferenceName, preferenceValue).apply();
        }
    }

    public static String getFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstants.APP_PREF_FILE_NAME,Context.MODE_PRIVATE);
        if(null != sharedPreferences) {
            return sharedPreferences.getString(preferenceName,defaultValue);
        }
        return defaultValue;
    }
}
