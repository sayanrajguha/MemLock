package com.sayanrajguha.nimbuscreations.memlock.constants;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public class AppConstants {

    public static final String APP_NAME = "MemLock";
    public static final String APP_VERSION = "1.0";
    public static final String APP_PREF_FILE_NAME = "com.sayanrajguha.nimbuscreations.memlock";

    //Fragment tags
    public static final String TAG_FRAGMENT_MEMOLIST = "MemoListFrag";
    public static final String TAG_FRAGMENT_MEMOCONTENT = "MemoContentFrag";
    public static final String TAG_FRAGMENT_CHANGEPASSWORD = "ChangePassFrag";
    public static final String TAG_FRAGMENT_NAVDRAWER = "NavDrawerFrag";

    //Navigation Drawer
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    //Database params
    public static final String DATABASE_NAME="memlock_db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_USER = "user";
    public static final String TABLE_MEMO = "memo";

    public static final String USER_KEY_ID = "_id";
    public static final String USER_KEY_USERNAME = "username";
    public static final String USER_KEY_PASSWORD = "password";

    public static final String MEMO_KEY_ID="_id";
    public static final String MEMO_KEY_SUBJECT = "subject";
    public static final String MEMO_KEY_DESC = "description";
    public static final String MEMO_KEY_CONTENT = "content";

}

