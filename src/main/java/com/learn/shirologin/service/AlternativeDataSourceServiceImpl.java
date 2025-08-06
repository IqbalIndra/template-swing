package com.learn.shirologin.service;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.StatusAlternative;
import com.learn.shirologin.repository.AlternativeDataSourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlternativeDataSourceServiceImpl implements AlternativeDataSourceService{
    private final AlternativeDataSourceRepository alternativeDataSourceRepository;

    @Override
    public Page<AlternativeDataSource> getAllAlternativeDataSource(Pageable pageable) {
        return alternativeDataSourceRepository.findByPagination(pageable);
    }

    @Override
    public AlternativeDataSource save(AlternativeDataSource alternativeDataSource) {
        alternativeDataSource.setStatus(StatusAlternative.ON_PROCESS);
        return alternativeDataSourceRepository.save(alternativeDataSource);
    }

    @Override
    public AlternativeDataSource update(AlternativeDataSource alternativeDataSource) {
        return alternativeDataSourceRepository.update(alternativeDataSource);
    }

    @Override
    public void delete(AlternativeDataSource alternativeDataSource) {
        alternativeDataSourceRepository.findById(alternativeDataSource.getId())
                .orElseThrow(() -> new RuntimeException("Not Found"));
        alternativeDataSourceRepository.delete(alternativeDataSource.getId());
    }
}
