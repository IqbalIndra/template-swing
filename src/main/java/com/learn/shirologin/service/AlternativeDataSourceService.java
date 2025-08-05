package com.learn.shirologin.service;

import com.learn.shirologin.model.AlternativeDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlternativeDataSourceService {
    Page<AlternativeDataSource> getAllAlternativeDataSource(Pageable pageable);
    AlternativeDataSource save(AlternativeDataSource alternativeDataSource);
    AlternativeDataSource update(AlternativeDataSource alternativeDataSource);
    void delete(AlternativeDataSource alternativeDataSource);
}
