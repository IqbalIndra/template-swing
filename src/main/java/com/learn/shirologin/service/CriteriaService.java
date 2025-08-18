package com.learn.shirologin.service;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CriteriaService {
    List<CriteriaItem> findCriteriaItem();
    List<CriteriaItem> findCriteriaItemByIds(long[] ids);
    List<Criteria> findAll();
    Page<Criteria> findByPagination(Pageable pageable);
    Criteria save(Criteria criteria);
    Criteria update(Criteria criteria);
    void delete(Criteria criteria);
}
