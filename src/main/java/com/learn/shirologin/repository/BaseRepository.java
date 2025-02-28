/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface BaseRepository<U,T> {
    Optional<T> findById(U id);
    T save(T t);
    List<T> findAll();
}
