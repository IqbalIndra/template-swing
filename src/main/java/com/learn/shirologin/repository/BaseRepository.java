/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface BaseRepository<U,T> {
    Optional<T> findById(U id);
    T save(T t);
    T update(T t);
    void delete(U id);
    List<T> findAll();
    Page<T> findByPagination(Pageable pageable);
}
