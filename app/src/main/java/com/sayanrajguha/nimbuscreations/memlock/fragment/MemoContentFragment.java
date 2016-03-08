package com.sayanrajguha.nimbuscreations.memlock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sayanrajguha.nimbuscreations.memlock.R;
import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.service.BackgroundMemoTask;
import com.sayanrajguha.nimbuscreations.memlock.service.MessageService;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/10/2016.
 */
public class MemoContentFragment extends Fragment implements View.OnClickListener {
    public static final String KEY_LOG = "- MEMO CONTENT FRAGMENT -";

    EditText mSubject;
    EditText mDesc;
    EditText mContent;
    ImageButton mSaveButton;
    ImageButton mCancelButton;
    TextView mMemoID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_content,container,false);

        mSubject = (EditText) view.findViewById(R.id.etMemoSubject);
        mDesc = (EditText) view.findViewById(R.id.etMemoDescription);
        mContent = (EditText) view.findViewById(R.id.etMemoBody);
        mSaveButton = (ImageButton) view.findViewById(R.id.fabSaveMemo);
        mCancelButton = (ImageButton) view.findViewById(R.id.fabCancelMemo);
        mMemoID = (TextView) view.findViewById(R.id.tvMemoID);

        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        return view;
    }

    public void setData(long id, String subject, String description, String content) {
        mSubject.setText(subject);
        mDesc.setText(description);
        mContent.setText(content);
        mMemoID.setText(String.valueOf(id));
    }

    @Override
    public void onClick(View v) {

        MessageService.log(KEY_LOG, "Click listened! ID : " + v.getId());
        Object[] requestObject = new Object[3];
        Object response = null;
        switch (v.getId()) {
            case R.id.fabSaveMemo :
                MessageService.log(KEY_LOG,"Save Clicked! ");
                Memo memo = getFormData();

                BackgroundMemoTask task = new BackgroundMemoTask(getActivity());
                if(memo!=null && memo.getId()==-1) {
                    requestObject[0]=BackgroundMemoTask.FLAG_ADD;
                    requestObject[1]=memo;
                    requestObject[2]=null;
                    response = task.execute(requestObject);
                } else if(memo!=null && memo.getId()!=-1) {
                    requestObject[0] = BackgroundMemoTask.FLAG_UPDATE;
                    requestObject[1] = memo;
                    requestObject[2] = memo.getId();
                    response = task.execute(requestObject);
                }
                break;
            case R.id.fabCancelMemo :
                MessageService.log(KEY_LOG,"Cancel clicked!");
                break;
            default:
        }
    }

    private Memo getFormData() {
        Memo memo = new Memo();
        String id = mMemoID.getText().toString();
        if(id!= null && !id.trim().isEmpty()) {
            memo.setId(Long.valueOf(id));
        } else {
            memo.setId(-1);
        }
        memo.setTitle(mSubject.getText().toString().trim());
        memo.setDescription(mDesc.getText().toString().trim());
        memo.setContent(mContent.getText().toString().trim());
        return memo;
    }
}
