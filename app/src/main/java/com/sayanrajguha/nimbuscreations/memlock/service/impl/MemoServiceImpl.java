package com.sayanrajguha.nimbuscreations.memlock.service.impl;

import com.sayanrajguha.nimbuscreations.memlock.model.Memo;
import com.sayanrajguha.nimbuscreations.memlock.service.MemoService;

import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public class MemoServiceImpl implements MemoService {
    private static final String KEY_LOG = "- Memo Service IMPL -";

    @Override
    public boolean addMemo(Memo memo) {
        return false;
    }

    @Override
    public boolean updateMemo(long sourceID, Memo updateMemo) {
        return false;
    }

    @Override
    public boolean deleteMemo(long id) {
        return false;
    }

    @Override
    public Memo getMemo(long id) {
        return null;
    }

    @Override
    public List<Memo> getAll() {
        return null;
    }
}
