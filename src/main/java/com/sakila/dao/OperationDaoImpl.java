package com.sakila.dao;

//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

//import java.math.BigDecimal;
//import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OperationDaoImpl implements OperationDao {
    NamedParameterJdbcTemplate template;

    public OperationDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Map<String, Object>> getOverdueRental() {
        String sql = "";
        sql = "SELECT  coalesce(c.last_name|| ', ', '') || c.first_name  AS  customer, coalesce(a.phone,'') as phone, f.title";
        sql = sql + " FROM PUBLIC.rental AS r";
        sql = sql + " INNER JOIN PUBLIC.customer AS c";
        sql = sql + " ON c.customer_id = r.customer_id";
        sql = sql + " INNER JOIN PUBLIC.address AS a";
        sql = sql + " ON a.address_id = c.address_id";
        sql = sql + " INNER  JOIN  PUBLIC.inventory AS i";
        sql = sql + " ON r.inventory_id = i.inventory_id";
        sql = sql + " INNER JOIN PUBLIC.film AS f";
        sql = sql + " ON f.film_id = i.film_id";
        sql = sql + " WHERE  r.return_date IS NULL";
        sql = sql
                + " AND (r.rental_date + ((f.rental_duration)::CHARACTER VARYING ||  ' days')::INTERVAL)::DATE < CURRENT_DATE;";

        return template.query(sql, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("customer", resultSet.getString("customer"));
            map.put("phone", resultSet.getString("phone"));
            map.put("title", resultSet.getString("title"));
            return map;
        });
    }

}
