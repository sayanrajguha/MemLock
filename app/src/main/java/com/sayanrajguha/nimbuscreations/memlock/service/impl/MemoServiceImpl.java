package com.sayanrajguha.nimbuscreations.memlock.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sayanrajguha.nimbuscreations.memlock.constants.AppConstants;
import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.service.MemoService;
import com.sayanrajguha.nimbuscreations.memlock.service.MessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public class MemoServiceImpl implements MemoService {
    private static final String KEY_LOG = "- Memo Service IMPL -";

    private Context context;
    private MemoHelper memoHelper;

    public MemoServiceImpl(Context context) {
        this.context = context;
        memoHelper = new MemoHelper(context);
    }

    @Override
    public boolean addMemo(Memo memo) {

        boolean addStatus = false;

        SQLiteDatabase db = memoHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConstants.MEMO_KEY_SUBJECT,memo.getTitle());
        contentValues.put(AppConstants.MEMO_KEY_DESC,memo.getDescription());
        contentValues.put(AppConstants.MEMO_KEY_CONTENT,memo.getContent());

        long addID = db.insert(AppConstants.TABLE_MEMO,null,contentValues);
        if(addID > 0) {
            addStatus = true;
        }

        db.close();
        return addStatus;
    }

    @Override
    public boolean updateMemo(long sourceID, Memo updateMemo) {

        boolean updateStatus = false;
        SQLiteDatabase db = memoHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppConstants.MEMO_KEY_SUBJECT,updateMemo.getTitle());
        contentValues.put(AppConstants.MEMO_KEY_DESC,updateMemo.getDescription());
        contentValues.put(AppConstants.MEMO_KEY_CONTENT,updateMemo.getContent());
        long updateID = db.update(AppConstants.TABLE_MEMO,contentValues,AppConstants.MEMO_KEY_ID + "=?",new String[]{""+sourceID});
        if(updateID > 0) {
            updateStatus = true;
        }
        return updateStatus;
    }

    @Override
    public boolean deleteMemo(long id) {

        boolean deleteStatus = false;
        SQLiteDatabase db = memoHelper.getWritableDatabase();
        long deleteID = db.delete(AppConstants.TABLE_MEMO,AppConstants.MEMO_KEY_ID + "=?",new String[]{""+id});
        if(deleteID > 0) {
            deleteStatus = true;
        }
        return deleteStatus;
    }

    @Override
    public Memo getMemo(long id) {

        Memo memo = null;
        SQLiteDatabase db = memoHelper.getReadableDatabase();
        Cursor memoCursor = db.query(AppConstants.TABLE_MEMO,null,AppConstants.MEMO_KEY_ID
                + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(null != memoCursor && memoCursor.moveToFirst()) {
            memo = new Memo();
            memo.setId(memoCursor.getLong(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_ID)));
            memo.setTitle(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_SUBJECT)));
            memo.setDescription(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_DESC)));
            memo.setContent(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_CONTENT)));
        }
        return memo;
    }

    @Override
    public List<Memo> getAll() {
        ArrayList<Memo> memoList = null;

        SQLiteDatabase db = memoHelper.getReadableDatabase();
        Cursor memoCursor = db.query(AppConstants.TABLE_MEMO,null,null,null,null,null,null,null);
        if(null != memoCursor) {
            memoList = new ArrayList<>();
            Memo memo = new Memo();
            while(memoCursor.moveToNext()) {
                memo.setId(memoCursor.getLong(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_ID)));
                memo.setTitle(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_SUBJECT)));
                memo.setDescription(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_DESC)));
                memo.setContent(memoCursor.getString(memoCursor.getColumnIndex(AppConstants.MEMO_KEY_CONTENT)));
                memoList.add(memo);
            }
        }

        return memoList;
    }

    static class MemoHelper extends SQLiteOpenHelper {

        private static final String KEY_LOG = "- Memo Helper -";

        private String query;
        public MemoHelper(Context context) {
            super(context, AppConstants.DATABASE_NAME,null,AppConstants.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String methodName = "onCreate()";
            query = "CREATE TABLE " +AppConstants.TABLE_MEMO+ "(" +AppConstants.MEMO_KEY_ID+ " INTEGER PRIMARY KEY," +
                    AppConstants.MEMO_KEY_SUBJECT+ " TEXT," +AppConstants.MEMO_KEY_DESC+ "TEXT," +
                    AppConstants.MEMO_KEY_CONTENT +"TEXT)";
            if(null != db) {
                try {
                    db.execSQL(query);
                } catch (SQLException sqlE) {
                    MessageService.log(KEY_LOG + " : " + methodName, "Exception thrown : " + sqlE);
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String methodName = "onUpgrade()";
            query = "DROP TABLE IF EXISTS "+AppConstants.TABLE_MEMO;
            if(null != db) {
                try {
                    db.execSQL(query);
                    onCreate(db);
                } catch (SQLException sqlE) {
                    MessageService.log(KEY_LOG + " : " +methodName,"Exception thrown : "+sqlE);
                }
            }
        }
    }
}
