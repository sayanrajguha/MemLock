package com.sayanrajguha.nimbuscreations.memlock.service;

import android.content.Context;
import android.os.AsyncTask;

import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.service.impl.MemoServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 2/2/2016.
 */
public class BackgroundMemoTask extends AsyncTask<Object,Integer,Object> {
    private static final String KEY_LOG = "- Background Memo Task -";

    public Context context;

    public static final String FLAG_ADD = "add";
    public static final String FLAG_UPDATE = "update";
    public static final String FLAG_DELETE = "delete";
    public static final String FLAG_GET = "get";
    public static final String FLAG_GET_ALL = "getAll";

    public BackgroundMemoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Object doInBackground(Object... params) {
        boolean status = false;
        Memo memoData=null;
        List<Memo> memoList = null;
        Object[] resultObjects = new Object[3];
        if(null != params && params.length > 1 && params[0] instanceof String) {
            String taskOption = String.valueOf(params[0]);
            MemoServiceImpl memoService = new MemoServiceImpl(this.context);

            switch (taskOption) {
                case FLAG_ADD :
                    if(params[1] instanceof Memo) {
                        status = memoService.addMemo((Memo) params[1]);
                    }
                    break;
                case FLAG_UPDATE :
                    if(params[1] instanceof Memo && params[2] instanceof Long) {
                        status = memoService.updateMemo((Long)params[2],(Memo)params[1]);
                    }
                    break;
                case FLAG_DELETE :
                    if(params[1] instanceof Long) {
                        status = memoService.deleteMemo((Long)params[1]);
                    }
                    break;
                case FLAG_GET :
                    if(params[1] instanceof Long) {
                        memoData = memoService.getMemo((Long)params[1]);
                    }
                    break;
                case FLAG_GET_ALL :
                    if(params[1] instanceof ArrayList) {
                        memoList = memoService.getAll();
                        if(memoList!=null && memoList.size()>=1) {
                            status = true;
                        }
                    }
                    break;
                default:
            }
        }
        resultObjects[0] = status;
        resultObjects[1] = memoData;
        resultObjects[2] = memoList;
        return resultObjects;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Object o) {

    }
}
