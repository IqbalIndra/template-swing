/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.shirologin.repository;

import com.learn.shirologin.model.Role;
import com.learn.shirologin.model.UserInfo;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KBDSI-IQBAL
 */
@Repository
@AllArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserInfo> findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                "select * from user_info where username = ?",
                (rs, rowNum) ->
                        Optional.of(
                                UserInfo.of()
                                .username(rs.getString("username"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .id(rs.getLong("id"))
                                .role(Role.valueOfRole(rs.getString("role")))
                                .build()
                        ),
                new Object[]{username}
               
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
       
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                "select * from user_info where id = ?",
                (rs, rowNum) ->
                        Optional.of(
                                UserInfo.of()
                                .username(rs.getString("username"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .id(rs.getLong("id"))
                                .role(Role.valueOfRole(rs.getString("role")))
                                .build()
                        ),
                new Object[]{id}
               
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserInfo save(UserInfo t) {
        String sql = "INSERT INTO user_info (username,email,password,role) VALUES (?,?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
              .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, t.getUsername());
              ps.setString(2, t.getEmail());
              ps.setString(3, t.getPassword());
              ps.setString(4, t.getRole().name());
              
              return ps;
            }, keyHolder);
        
        t.setId((long)keyHolder.getKey());
            
        return t;
     
    }

    @Override
    public List<UserInfo> findAll() {
        return jdbcTemplate.query(
                "select * from user_info",
                (rs, rowNum) ->
                        UserInfo.of()
                                .username(rs.getString("username"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .id(rs.getLong("id"))
                                .role(Role.valueOfRole(rs.getString("role")))
                                .build()
               
        );
    }

    @Override
    public Page<UserInfo> findByPagination(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " +
                "FROM user_info ";
        int total =
                jdbcTemplate.queryForObject(
                        rowCountSql, (rs, rowNum) -> rs.getInt(1)
                );

        String querySql = "SELECT * " +
                "FROM user_info " +
                "LIMIT " + pageable.getPageSize() + " " +
                "OFFSET " + pageable.getOffset();
        List<UserInfo> demos = jdbcTemplate.query(
                querySql, 
                (rs, rowNum) -> UserInfo.of()
                                .username(rs.getString("username"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .id(rs.getLong("id"))
                                .role(Role.valueOfRole(rs.getString("role")))
                                .build()
        );

        return new PageImpl<>(demos, pageable, total);
    }

    @Override
    public UserInfo update(UserInfo t) {
        String sql = "UPDATE user_info SET username=?,email=?,email=?,role=? WHERE id=? ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
              .prepareStatement(sql);
              ps.setString(1, t.getUsername());
              ps.setString(2, t.getEmail());
              ps.setString(3, t.getPassword());
              ps.setString(4, t.getRole().name());
              ps.setLong(5, t.getId());
              
              
              return ps;
            });
        
        return t;
    }
    
}
