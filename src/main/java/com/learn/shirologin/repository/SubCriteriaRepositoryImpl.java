package com.learn.shirologin.repository;

import com.learn.shirologin.model.Criteria;
import com.learn.shirologin.model.CriteriaType;
import com.learn.shirologin.model.OperatorType;
import com.learn.shirologin.model.SubCriteria;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SubCriteriaRepositoryImpl implements SubCriteriaRepository{
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<SubCriteria> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select a.*, b.name as criteria_name from swa_sub_criteria a" +
                            " INNER JOIN swa_criteria b ON a.swa_criteria_id = b.id " +
                            " where a.id = ?",
                    (rs, rowNum) ->
                            Optional.of(
                                    SubCriteria.of()
                                            .name(rs.getString("name"))
                                            .weight(rs.getDouble("weight"))
                                            .minValue(rs.getDouble("min_value"))
                                            .maxValue(rs.getDouble("max_value"))
                                            .id(rs.getLong("id"))
                                            .operator(OperatorType.valueOfType(rs.getString("operator")))
                                            .criteria(Criteria.of()
                                                    .id(rs.getLong("swa_criteria_id"))
                                                    .name(rs.getString("criteria_name"))
                                                    .build())
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
    public SubCriteria save(SubCriteria subCriteria) {
        String sql = "INSERT INTO swa_sub_criteria (" +
                "name,weight,min_value,max_value,operator,swa_criteria_id,is_deleted" +
                ") VALUES (?,?,?,?,?,?,false) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subCriteria.getName());
            ps.setDouble(2, subCriteria.getWeight());
            ps.setDouble(3, subCriteria.getMinValue());
            ps.setDouble(4, subCriteria.getMaxValue());
            ps.setString(5, subCriteria.getOperator().getSymbol());
            ps.setLong(6, subCriteria.getCriteria().getId());

            return ps;
        }, keyHolder);

        subCriteria.setId((long)keyHolder.getKey());

        return subCriteria;
    }

    @Override
    public SubCriteria update(SubCriteria subCriteria) {
        String sql = "UPDATE swa_sub_criteria SET name=?,weight=?,min_value=?," +
                "max_value=?,operator=?,swa_criteria_id=? WHERE id=? ";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setString(1, subCriteria.getName());
            ps.setDouble(2, subCriteria.getWeight());
            ps.setDouble(3, subCriteria.getMinValue());
            ps.setDouble(4, subCriteria.getMaxValue());
            ps.setString(5, subCriteria.getOperator().getSymbol());
            ps.setLong(6, subCriteria.getCriteria().getId());
            ps.setLong(7, subCriteria.getId());

            return ps;
        });

        return subCriteria;
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE swa_sub_criteria SET is_deleted=? WHERE id=? ";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setLong(2, id);

            return ps;
        });
    }

    @Override
    public List<SubCriteria> findAll() {
        return jdbcTemplate.query(
                "select a.*, b.name as criteria_name from swa_sub_criteria a" +
                        " INNER JOIN swa_criteria b ON a.swa_criteria_id = b.id " +
                        " where a.is_deleted = false",
                (rs, rowNum) ->
                        SubCriteria.of()
                                .name(rs.getString("name"))
                                .weight(rs.getDouble("weight"))
                                .minValue(rs.getDouble("min_value"))
                                .maxValue(rs.getDouble("max_value"))
                                .id(rs.getLong("id"))
                                .operator(OperatorType.valueOfType(rs.getString("operator")))
                                .criteria(Criteria.of()
                                        .id(rs.getLong("swa_criteria_id"))
                                        .name(rs.getString("criteria_name"))
                                        .build())
                                .deleted(rs.getBoolean("is_deleted"))
                                .build()

        );
    }

    @Override
    public Page<SubCriteria> findByPagination(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " +
                "FROM swa_sub_criteria WHERE is_deleted = false ";
        int total =
                jdbcTemplate.queryForObject(
                        rowCountSql, (rs, rowNum) -> rs.getInt(1)
                );

        String querySql = "SELECT a.*, b.name as criteria_name " +
                "FROM swa_sub_criteria a INNER JOIN swa_criteria b " +
                "ON a.swa_criteria_id = b.id WHERE a.is_deleted=false " +
                "LIMIT " + pageable.getPageSize() + " " +
                "OFFSET " + pageable.getOffset();
        List<SubCriteria> demos = jdbcTemplate.query(
                querySql,
                (rs, rowNum) -> SubCriteria.of()
                        .name(rs.getString("name"))
                        .weight(rs.getDouble("weight"))
                        .minValue(rs.getDouble("min_value"))
                        .maxValue(rs.getDouble("max_value"))
                        .id(rs.getLong("id"))
                        .operator(OperatorType.valueOfType(rs.getString("operator")))
                        .criteria(Criteria.of()
                                .id(rs.getLong("swa_criteria_id"))
                                .name(rs.getString("criteria_name"))
                                .build())
                        .deleted(rs.getBoolean("is_deleted"))
                        .build()
        );
        return new PageImpl<>(demos, pageable, total);
    }
}
