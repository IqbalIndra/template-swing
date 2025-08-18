package com.learn.shirologin.repository;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaItem;

import java.util.List;

public interface CriteriaRepository extends BaseRepository<Long, Criteria>{
    List<CriteriaItem> findCriteriaItem();
    List<CriteriaItem> findCriteriaItemIn(long[] ids);
}
