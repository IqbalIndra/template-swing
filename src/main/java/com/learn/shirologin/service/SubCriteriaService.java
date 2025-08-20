package com.learn.shirologin.service;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.model.SubCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubCriteriaService {
    List<SubCriteria> findAll();
    Page<SubCriteria> findByPagination(Pageable pageable);
    SubCriteria save(SubCriteria subCriteria);
    SubCriteria update(SubCriteria subCriteria);
    void delete(SubCriteria subCriteria);
}
