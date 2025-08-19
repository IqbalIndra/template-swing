package com.learn.shirologin.repository;

import com.learn.shirologin.model.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CriteriaRepositoryImpl implements CriteriaRepository{
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Criteria> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from swa_criteria where id = ?",
                    (rs, rowNum) ->
                            Optional.of(
                                    Criteria.of()
                                            .name(rs.getString("name"))
                                            .weight(rs.getDouble("weight"))
                                            .id(rs.getLong("id"))
                                            .type(CriteriaType.valueOfType(rs.getString("type")))
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
    public Criteria save(Criteria criteria) {
        String sql = "INSERT INTO swa_criteria (" +
                "name,type,weight,is_deleted" +
                ") VALUES (?,?,?,false) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, criteria.getName());
            ps.setString(2, criteria.getType().getName());
            ps.setDouble(3, criteria.getWeight());
            return ps;
        }, keyHolder);

        criteria.setId((long)keyHolder.getKey());

        return criteria;
    }

    @Override
    public Criteria update(Criteria criteria) {
        String sql = "UPDATE swa_criteria SET name=?,type=?,weight=? WHERE id=? ";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setString(1, criteria.getName());
            ps.setString(2, criteria.getType().getName());
            ps.setDouble(3, criteria.getWeight());
            ps.setLong(4, criteria.getId());

            return ps;
        });

        return criteria;
    }

    @Override
    public void delete(Long id) {
        String sql = "UPDATE swa_criteria SET is_deleted=? WHERE id=? ";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setLong(2, id);

            return ps;
        });
    }

    @Override
    public List<Criteria> findAll() {
        return jdbcTemplate.query(
                "select * from swa_criteria WHERE is_deleted=false",
                (rs, rowNum) ->
                        Criteria.of()
                                .name(rs.getString("name"))
                                .weight(rs.getDouble("weight"))
                                .id(rs.getLong("id"))
                                .type(CriteriaType.valueOfType(rs.getString("type")))
                                .deleted(rs.getBoolean("is_deleted"))
                                .build()

        );
    }

    @Override
    public Page<Criteria> findByPagination(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " +
                "FROM swa_criteria WHERE is_deleted = false ";
        int total =
                jdbcTemplate.queryForObject(
                        rowCountSql, (rs, rowNum) -> rs.getInt(1)
                );

        String querySql = "SELECT * " +
                "FROM swa_criteria WHERE is_deleted=false " +
                "LIMIT " + pageable.getPageSize() + " " +
                "OFFSET " + pageable.getOffset();
        List<Criteria> demos = jdbcTemplate.query(
                querySql,
                (rs, rowNum) -> Criteria.of()
                        .name(rs.getString("name"))
                        .weight(rs.getDouble("weight"))
                        .id(rs.getLong("id"))
                        .type(CriteriaType.valueOfType(rs.getString("type")))
                        .deleted(rs.getBoolean("is_deleted"))
                        .build()
        );
        return new PageImpl<>(demos, pageable, total);
    }

    @Override
    public List<CriteriaItem> findCriteriaItem() {
        List<CriteriaItem> criteriaItems = new ArrayList<>();
        long id = 0;
        CriteriaItem criteriaItem = null;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "select a.id, a.name, a.type, a.weight, b.operator, b.weight AS sub_weight, " +
                        "b.min_value, b.max_value from swa_criteria a LEFT JOIN swa_sub_criteria b " +
                        "ON a.id = b.swa_criteria_id WHERE a.is_deleted=false order by a.id"
        );

        for(Map<String,Object> map : rows){
            SubCriteriaItem subCriteriaItem = SubCriteriaItem.of()
                    .operator(OperatorType.valueOfType((String) map.get("operator")))
                    .weight((Double) map.get("sub_weight"))
                    .minValue((Double)map.get("min_value"))
                    .maxValue((Double)map.get("max_value"))
                    .build();

            if(id != (Long) map.get("id")) {
                id = (Long) map.get("id");

                criteriaItem = CriteriaItem.of().build();
                criteriaItem.setType(CriteriaType.valueOfType((String)map.get("type")));
                criteriaItem.setWeight((Double) map.get("weight"));
                criteriaItem.setSubCriteriaItems(new ArrayList<>());
                criteriaItem.setId(id);
                criteriaItem.setText((String) map.get("name"));
                criteriaItems.add(criteriaItem);
            }
            criteriaItem.getSubCriteriaItems().add(subCriteriaItem);

        }
        return criteriaItems;
    }

    @Override
    public List<CriteriaItem> findCriteriaItemIn(long[] ids) {
        if(Objects.isNull(ids) || ids.length == 0)
            return new ArrayList<>();

        List<CriteriaItem> items = new ArrayList<>();
        String placeholders = String.join(",", Collections.nCopies(ids.length, "?"));
        Object[] idx = Arrays.stream(ids)
                .boxed().toArray();

        List<Map<String,Object>> rows = jdbcTemplate.queryForList(
                String.format("SELECT * FROM swa_criteria WHERE id IN (%s)",placeholders)
                , idx
        );

        for(Map<String,Object> map : rows){
            CriteriaItem criteriaItem = CriteriaItem.of().build();
            criteriaItem.setType(CriteriaType.valueOfType((String)map.get("TYPE")));
            criteriaItem.setWeight((Double) map.get("WEIGHT"));
            criteriaItem.setSubCriteriaItems(new ArrayList<>());
            criteriaItem.setId((Long)map.get("ID"));
            criteriaItem.setText((String) map.get("NAME"));
            items.add(criteriaItem);
        }

        return items;
    }
}
