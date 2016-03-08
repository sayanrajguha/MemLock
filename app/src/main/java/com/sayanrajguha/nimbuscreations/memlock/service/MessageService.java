package com.sayanrajguha.nimbuscreations.memlock.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 2/1/2016.
 */
public class MessageService {

    public static void log(String TAG, String message) {
        if(null != TAG) {
            Log.d(TAG, message);
        } else {
            Log.e(MessageService.class.getName(), "Class name for logging not found. Message found is : " + message);
        }
    }

    public static void message(Context context, String message) {
        if(null != context && null != message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            Log.e(MessageService.class.getName(),"Could not make Toast with empty parameters");
        }
    }

}
