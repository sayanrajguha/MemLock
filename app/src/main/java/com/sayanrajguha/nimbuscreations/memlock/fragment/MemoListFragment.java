package com.sayanrajguha.nimbuscreations.memlock.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayanrajguha.nimbuscreations.memlock.R;
import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.model.ServiceResponse;
import com.sayanrajguha.nimbuscreations.memlock.service.BackgroundMemoTask;
import com.sayanrajguha.nimbuscreations.memlock.service.MessageService;

import java.util.ArrayList;
import java.util.List;

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
    RecyclerView mMemoList = null;
    MemoDetailsFetcher detailsFetcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MessageService.log(KEY_LOG,"onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_memo_list,container,false);
        mFABAddBtn = (ImageButton) view.findViewById(R.id.fabAddMemo);
        mListLayout = (LinearLayout) view.findViewById(R.id.llList);
        mMemoList = (RecyclerView) view.findViewById(R.id.rvMemoList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mMemoList.setLayoutManager(layoutManager);


        BackgroundMemoTask task = new BackgroundMemoTask(getActivity(),this);
        Object[] requestParams = new Object[3];
        requestParams[0] = BackgroundMemoTask.FLAG_GET_ALL;
        requestParams[1] = new ArrayList();
        requestParams[2] = null;
        task.execute(requestParams);

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
                        detailsFetcher.fetchMemo(-1);
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

    public void processServiceCallback(ServiceResponse response) {
        if(null != response) {
            MessageService.log(KEY_LOG,"STATUS : "+response.isStatus());
            MessageService.log(KEY_LOG,"FLAG : "+response.getFlag());
            if(response.isStatus() && response.getFlag().equalsIgnoreCase(BackgroundMemoTask.FLAG_GET_ALL)) {
                List<Memo> list = response.getMemoList();
                showList(list);
                populateList(list);
            }
        }
    }

    private void showList(List<Memo> list) {
       if(null != list) {
           MessageService.log(KEY_LOG,"List size : "+list.size());
       }
        /*int i=1;
        for (Memo memo : list) {
            MessageService.log(KEY_LOG,"No : "+i);
            MessageService.log(KEY_LOG,"ID : "+memo.getId());
            MessageService.log(KEY_LOG,"Title : "+memo.getTitle());
            MessageService.log(KEY_LOG,"Desc : "+memo.getDescription());
            MessageService.log(KEY_LOG,"Content : "+memo.getContent());
            i++;
        }*/
    }

    private void populateList(List<Memo> memoList) {
        MemoListAdapter adapter = new MemoListAdapter(memoList);
        mMemoList.setAdapter(adapter);
    }

    public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.MemoViewHolder> {
        public static final String KEY_LOG = "- MEMO LIST ADAPTER -";

        private List<Memo> memoList;
        public MemoListAdapter(List<Memo> memoList) {
            this.memoList = memoList;
        }

        @Override
        public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_card_layout,parent,false);
            MemoViewHolder viewHolder = new MemoViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MemoViewHolder holder, int position) {
            if(null != this.memoList && getItemCount() > 0) {
                Memo memoData = this.memoList.get(position);
                holder.mMemoID.setText(String.valueOf(memoData.getId()));
                holder.mMemoTitle.setText(memoData.getTitle());
                holder.mMemoDesc.setText(memoData.getDescription());
                //holder.mMemoContent.setText(memoData.getContent());
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            MessageService.log(this.KEY_LOG,"ATTACHED TO RECYCLERVIEW");
        }

        @Override
        public int getItemCount() {
            if(null != memoList) {
                return memoList.size();
            }
            return 0;
        }

        public class MemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CardView mMemoCard;
            TextView mMemoID;
            TextView mMemoTitle;
            TextView mMemoDesc;
            //TextView mMemoContent;

            public MemoViewHolder(View itemView) {
                super(itemView);
                mMemoCard = (CardView) itemView.findViewById(R.id.cvMemoCard);
                mMemoID = (TextView) itemView.findViewById(R.id.tvMemoID);
                mMemoTitle = (TextView) itemView.findViewById(R.id.tvMemoSub);
                mMemoDesc = (TextView) itemView.findViewById(R.id.tvMemoDesc);
                //mMemoContent = (TextView) itemView.findViewById(R.id.tvMemoContent);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if(detailsFetcher != null) {
                    detailsFetcher.fetchMemo(Long.valueOf(mMemoID.getText().toString()));
                }
            }
        }

    }
}
