package com.sayanrajguha.nimbuscreations.memlock.service;

import com.sayanrajguha.nimbuscreations.memlock.model.Memo;

import java.util.List;

/**
 * Author # Sayanraj Guha
 * © sayanrajguha@gmail.com
 * ® nimbusCreations
 * Created by SA299562 on 1/3/2016.
 */
public interface MemoService {

    boolean addMemo(Memo memo);

    boolean updateMemo(long sourceID, Memo updateMemo);

    boolean deleteMemo(long id);

    Memo getMemo(long id);

    List<Memo> getAll();
}
