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
import com.sayanrajguha.nimbuscreations.memlock.model.ServiceResponse;
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
        MessageService.log(KEY_LOG,"onCreateView() caled");
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

    public void setData(Memo memo) {
       //MessageService.log(KEY_LOG, mSubject + " (sub) "+mDesc+" (desc) "+mContent+" (content) "+mMemoID+" (id)");
        mSubject.setText(memo.getTitle());
        mDesc.setText(memo.getDescription());
        mContent.setText(memo.getContent());
        mMemoID.setText(String.valueOf(memo.getId()));
    }

    public boolean fetchData(long id) {
        boolean fetchStatus = false;
        Memo memoDataObject = new Memo();
        if(id == -1) {
            MessageService.log(KEY_LOG,"New Memo creation ");
            memoDataObject.setId(-1);
            memoDataObject.setTitle("");
            memoDataObject.setContent("");
            memoDataObject.setDescription("");
        } else {
            //logic for getting data from DB
            BackgroundMemoTask task = new BackgroundMemoTask(getActivity(),this);
            Object[] requestObject = new Object[3];
            requestObject[0] = BackgroundMemoTask.FLAG_GET;
            requestObject[1] = id;
            task.execute(requestObject);
        }
        setData(memoDataObject);
        return fetchStatus;
    }

    @Override
    public void onClick(View v) {

        MessageService.log(KEY_LOG, "Click listened! ID : " + v.getId());
        Object[] requestObject = null;
        ServiceResponse response = null;
        BackgroundMemoTask task = new BackgroundMemoTask(getActivity(),this);
        switch (v.getId()) {
            case R.id.fabSaveMemo :
                requestObject = new Object[3];
                MessageService.log(KEY_LOG,"Save Clicked! ");
                Memo memo = getFormData();
                MessageService.log(KEY_LOG," ID : "+memo.getId());
                MessageService.log(KEY_LOG," Subject : "+memo.getTitle());
                MessageService.log(KEY_LOG," Desc : "+memo.getDescription());
                MessageService.log(KEY_LOG," Content : "+memo.getContent());

                if(memo!=null && memo.getId()==-1) {
                    requestObject[0]=BackgroundMemoTask.FLAG_ADD;
                    requestObject[1]=memo;
                    requestObject[2]=null;

                } else if(memo!=null && memo.getId()!=-1) {
                    requestObject[0] = BackgroundMemoTask.FLAG_UPDATE;
                    requestObject[1] = memo;
                    requestObject[2] = memo.getId();
                }
                break;
            case R.id.fabCancelMemo :
                MessageService.log(KEY_LOG,"Cancel clicked!");
                break;
            default:
        }
        if(null != requestObject) {
            task.execute(requestObject);
        }
        /*if(null != response && response instanceof Object[]) {
            Object[] responseArray = (Object[]) response;
            if(responseArray[0] instanceof Boolean && responseArray[1] instanceof Memo && responseArray[2] instanceof ArrayList) {
                boolean status = (boolean) responseArray[0];
                Memo memoData = (Memo) responseArray[1];
                ArrayList<Memo> memoList = (ArrayList<Memo>) responseArray[2];

                if() {

                }

            }
        }*/
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

    public void processServiceCallback(ServiceResponse response) {
        MessageService.log(KEY_LOG,"STATUS : "+response.isStatus());
        MessageService.log(KEY_LOG, "FLAG : " + response.getFlag());
        if(response.getFlag().equalsIgnoreCase(BackgroundMemoTask.FLAG_ADD)) {
            getFragmentManager().popBackStackImmediate();
            MessageService.message(getActivity(), getResources().getString(R.string.lbl_MemoAdded));
        }
        else if(response.getFlag().equalsIgnoreCase(BackgroundMemoTask.FLAG_GET)) {
            MessageService.log(KEY_LOG,"Received memo id : "+response.getMemoData().getId());
            MessageService.log(KEY_LOG,"Received memo sub : "+response.getMemoData().getTitle());
            MessageService.log(KEY_LOG,"Received memo desc : "+response.getMemoData().getDescription());
            MessageService.log(KEY_LOG,"Received memo content : "+response.getMemoData().getContent());
            if(response.isStatus() && response.getMemoData() != null) {
                setData(response.getMemoData());
            }
        }
    }
}