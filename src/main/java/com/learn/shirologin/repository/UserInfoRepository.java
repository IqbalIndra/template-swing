/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.repository;

import com.learn.shirologin.model.UserInfo;
import java.util.Optional;

/**
 *
 * @author KBDSI-IQBAL
 */
public interface UserInfoRepository extends BaseRepository<Long, UserInfo>{
    Optional<UserInfo> findByUsername(String username);
}
