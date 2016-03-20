package com.sayanrajguha.nimbuscreations.memlock.service;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.sayanrajguha.nimbuscreations.memlock.fragment.MemoContentFragment;
import com.sayanrajguha.nimbuscreations.memlock.fragment.MemoListFragment;
import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.model.ServiceResponse;
import com.sayanrajguha.nimbuscreations.memlock.service.impl.MemoServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 2/2/2016.
 */
public class BackgroundMemoTask extends AsyncTask<Object,Integer,ServiceResponse> {
    private static final String KEY_LOG = "- Background Memo Task -";

    public Context context;
    public Fragment currentFragment;

    public static final String FLAG_ADD = "add";
    public static final String FLAG_UPDATE = "update";
    public static final String FLAG_DELETE = "delete";
    public static final String FLAG_GET = "get";
    public static final String FLAG_GET_ALL = "getAll";

    public BackgroundMemoTask(Context context, Fragment fragment) {
        this.context = context;
        this.currentFragment = fragment;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ServiceResponse doInBackground(Object... params) {
        Boolean status = false;
        Memo memoData=null;
        List<Memo> memoList = null;
        ServiceResponse result = new ServiceResponse();
        if(null != params && params.length > 1 && params[0] instanceof String) {
            String taskOption = String.valueOf(params[0]);
            MemoServiceImpl memoService = new MemoServiceImpl(this.context);
            MessageService.log(KEY_LOG,"FLAG : "+taskOption);
            result.setFlag(taskOption);
            switch (taskOption) {
                case FLAG_ADD :
                    MessageService.log(KEY_LOG,"Operation ADD");
                    if(params[1] instanceof Memo) {
                        status = memoService.addMemo((Memo) params[1]);
                    }
                    break;
                case FLAG_UPDATE :
                    MessageService.log(KEY_LOG,"Operation UPDATE");
                    if(params[1] instanceof Memo && params[2] instanceof Long) {
                        status = memoService.updateMemo((Long)params[2],(Memo)params[1]);
                    }
                    break;
                case FLAG_DELETE :
                    MessageService.log(KEY_LOG,"Operation DELETE");
                    if(params[1] instanceof Long) {
                        status = memoService.deleteMemo((Long)params[1]);
                    }
                    break;
                case FLAG_GET :
                    MessageService.log(KEY_LOG,"Operation GET");
                    if(params[1] instanceof Long) {
                        memoData = memoService.getMemo((Long)params[1]);
                        if(memoData != null) {
                            status = true;
                        }
                    }
                    break;
                case FLAG_GET_ALL :
                    MessageService.log(KEY_LOG,"Operation GETALL");
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

        result.setStatus(status);
        result.setMemoData(memoData);
        result.setMemoList(memoList);
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(ServiceResponse response) {
        if(null != this.currentFragment && this.currentFragment instanceof MemoContentFragment) {
            MemoContentFragment fragment = (MemoContentFragment) this.currentFragment;
            fragment.processServiceCallback(response);
        } else if(null != this.currentFragment && this.currentFragment instanceof MemoListFragment) {
            MemoListFragment fragment = (MemoListFragment) this.currentFragment;
            fragment.processServiceCallback(response);
        }
    }
}
