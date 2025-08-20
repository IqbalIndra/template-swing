package com.learn.shirologin.service;

import com.learn.shirologin.model.SubCriteria;
import com.learn.shirologin.repository.SubCriteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubCriteriaServiceImpl implements SubCriteriaService{
    private final SubCriteriaRepository subCriteriaRepository;

    @Override
    public List<SubCriteria> findAll() {
        return subCriteriaRepository.findAll();
    }

    @Override
    public Page<SubCriteria> findByPagination(Pageable pageable) {
        return subCriteriaRepository.findByPagination(pageable);
    }

    @Override
    public SubCriteria save(SubCriteria subCriteria) {
        return subCriteriaRepository.save(subCriteria);
    }

    @Override
    public SubCriteria update(SubCriteria subCriteria) {
        return subCriteriaRepository.update(subCriteria);
    }

    @Override
    public void delete(SubCriteria subCriteria) {
        subCriteriaRepository.findById(subCriteria.getId())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        subCriteriaRepository.delete(subCriteria.getId());
    }
}
