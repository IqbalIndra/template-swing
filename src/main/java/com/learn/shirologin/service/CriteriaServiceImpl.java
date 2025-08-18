package com.learn.shirologin.service;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaItem;
import com.learn.shirologin.repository.CriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CriteriaServiceImpl implements CriteriaService{
    private final CriteriaRepository criteriaRepository;

    @Override
    public List<CriteriaItem> findCriteriaItem() {
        return criteriaRepository.findCriteriaItem();
    }

    @Override
    public List<CriteriaItem> findCriteriaItemByIds(long[] ids) {
        return criteriaRepository.findCriteriaItemIn(ids);
    }

    @Override
    public List<Criteria> findAll() {
        return criteriaRepository.findAll();
    }

    @Override
    public Page<Criteria> findByPagination(Pageable pageable) {
        return criteriaRepository.findByPagination(pageable);
    }

    @Override
    public Criteria save(Criteria criteria) {
        return criteriaRepository.save(criteria);
    }

    @Override
    public Criteria update(Criteria criteria) {
        return criteriaRepository.update(criteria);
    }

    @Override
    public void delete(Criteria criteria) {
        criteriaRepository.findById(criteria.getId())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        criteriaRepository.delete(criteria.getId());
    }
}
