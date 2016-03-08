package com.sayanrajguha.nimbuscreations.memlock.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayanrajguha.nimbuscreations.memlock.R;
import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.service.BackgroundMemoTask;
import com.sayanrajguha.nimbuscreations.memlock.service.MessageService;

import java.util.ArrayList;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/10/2016.
 */
public class MemoListFragment extends Fragment implements View.OnClickListener {
    public static final String KEY_LOG = "- MEMO LIST FRAGMENT -";

    ImageButton mFABAddBtn;
    LinearLayout mListLayout = null;

    MemoDetailsFetcher detailsFetcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MessageService.log(KEY_LOG,"onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_memo_list,container,false);
        mFABAddBtn = (ImageButton) view.findViewById(R.id.fabAddMemo);
        mListLayout = (LinearLayout) view.findViewById(R.id.llList);
        mFABAddBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        MessageService.log(KEY_LOG, "onClick() called with id : " + v.getId());
        if(null != v) {
            switch (v.getId()) {
                case R.id.fabAddMemo:
                    MessageService.log(KEY_LOG,"FAB clicked!");

                    if(null != detailsFetcher) {
                        detailsFetcher.fetchMemo(v.getId());
                    } else {
                        MessageService.log(KEY_LOG,"detailsFetcher interface instance null");
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            detailsFetcher = (MemoDetailsFetcher) activity;
        }catch (ClassCastException cce) {
            MessageService.log(KEY_LOG,"Class cast exception occurred: "+cce);
        }
    }

    public interface MemoDetailsFetcher {
        void fetchMemo(long ID);
    }

    private void populateValues() {
        BackgroundMemoTask task = new BackgroundMemoTask(getActivity());
        Object[] requestParams = new Object[3];
        requestParams[0] = BackgroundMemoTask.FLAG_GET_ALL;
        requestParams[1] = new ArrayList();
        requestParams[2] = null;
        Object response = task.execute(requestParams);
        if(response!=null && response instanceof Object[]) {
            Object[] responseData = (Object[]) response;
            if(responseData[0]!=null && responseData[0] instanceof Boolean && responseData[2]!=null && responseData[2] instanceof ArrayList) {
                Boolean status = (Boolean) responseData[0];
                ArrayList<Memo> memoList = (ArrayList<Memo>) responseData[2];

                if(status) {
                    for (Memo memo: memoList) {
                        TextView tView = new TextView(getActivity());
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        tView.setLayoutParams(params);
                        tView.setText(memo.getTitle());
                        mListLayout.addView(tView);
                    }
                } else {

                }
            }
        }
    }

}
