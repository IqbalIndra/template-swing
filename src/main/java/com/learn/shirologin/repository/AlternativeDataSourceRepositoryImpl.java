package com.learn.shirologin.repository;

import com.learn.shirologin.model.AlternativeDataSource;
import com.learn.shirologin.model.StatusAlternative;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AlternativeDataSourceRepositoryImpl implements AlternativeDataSourceRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<AlternativeDataSource> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from swa_alternative_data_source where id = ?",
                    (rs, rowNum) ->
                            Optional.of(
                                    AlternativeDataSource.of()
                                            .major(rs.getString("major"))
                                            .code(rs.getString("code"))
                                            .classRoom(rs.getString("class_room"))
                                            .schoolYear(rs.getString("school_year"))
                                            .id(rs.getLong("id"))
                                            .status(StatusAlternative.valueOfStatus(rs.getString("status")))
                                            .inputStreamFileSource(rs.getBinaryStream("file_source"))
                                            .deleted(rs.getBoolean("is_deleted"))
                                            .build()
                            ),
                    new Object[]{id}

            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public AlternativeDataSource save(AlternativeDataSource alternativeDataSource) {
        String sql = "INSERT INTO swa_alternative_data_source (" +
                "major,code,class_room,school_year,status,file_source,is_deleted" +
                ") VALUES (?,?,?,?,?,?,false) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alternativeDataSource.getMajor());
            ps.setString(2, alternativeDataSource.getCode());
            ps.setString(3, alternativeDataSource.getClassRoom());
            ps.setString(4, alternativeDataSource.getSchoolYear());
            ps.setString(5, alternativeDataSource.getStatus().name());

            try (FileInputStream fis = new FileInputStream(alternativeDataSource.getFileSource())) {
                ps.setBinaryStream(6, fis, alternativeDataSource.getFileSource().length());
                return ps;
            }  catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, keyHolder);

        alternativeDataSource.setId((long)keyHolder.getKey());

        return alternativeDataSource;
    }

    @Override
    public AlternativeDataSource update(AlternativeDataSource alternativeDataSource) {
        String sql = "UPDATE swa_alternative_data_source SET major=?,code=?,class_room=?,school_year=?,status=?,file_source=? WHERE id=? ";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setString(1, alternativeDataSource.getMajor());
            ps.setString(2, alternativeDataSource.getCode());
            ps.setString(3, alternativeDataSource.getClassRoom());
            ps.setString(4, alternativeDataSource.getSchoolYear());
            ps.setString(5, alternativeDataSource.getStatus().name());

            try (FileInputStream fis = new FileInputStream(alternativeDataSource.getFileSource())) {
                ps.setBinaryStream(6, fis, alternativeDataSource.getFileSource().length());
                return ps;
            }  catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        return alternativeDataSource;
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE swa_alternative_data_source SET is_deleted=? WHERE id=? ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setLong(2, id);

            return ps;
        });
    }

    @Override
    public List<AlternativeDataSource> findAll() {
        return jdbcTemplate.query(
                "select * from swa_alternative_data_source WHERE is_deleted=false",
                (rs, rowNum) ->
                        AlternativeDataSource.of()
                            .major(rs.getString("major"))
                            .code(rs.getString("code"))
                            .classRoom(rs.getString("class_room"))
                            .schoolYear(rs.getString("school_year"))
                            .id(rs.getLong("id"))
                            .status(StatusAlternative.valueOfStatus(rs.getString("status")))
                            .inputStreamFileSource(rs.getBinaryStream("file_source"))
                            .deleted(rs.getBoolean("is_deleted"))
                            .build()

        );
    }

    @Override
    public Page<AlternativeDataSource> findByPagination(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " +
                "FROM swa_alternative_data_source WHERE is_deleted = false ";
        int total =
                jdbcTemplate.queryForObject(
                        rowCountSql, (rs, rowNum) -> rs.getInt(1)
                );

        String querySql = "SELECT * " +
                "FROM swa_alternative_data_source WHERE is_deleted=false " +
                "LIMIT " + pageable.getPageSize() + " " +
                "OFFSET " + pageable.getOffset();
        List<AlternativeDataSource> demos = jdbcTemplate.query(
                querySql,
                (rs, rowNum) -> AlternativeDataSource.of()
                        .major(rs.getString("major"))
                        .code(rs.getString("code"))
                        .classRoom(rs.getString("class_room"))
                        .schoolYear(rs.getString("school_year"))
                        .id(rs.getLong("id"))
                        .status(StatusAlternative.valueOfStatus(rs.getString("status")))
                        .inputStreamFileSource(rs.getBinaryStream("file_source"))
                        .deleted(rs.getBoolean("is_deleted"))
                        .build()
        );
        return new PageImpl<>(demos, pageable, total);
    }
}
