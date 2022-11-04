package com.sakila.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CatalogDaoImpl implements CatalogDao {

    private static final String STRING_Y = "Y";

    NamedParameterJdbcTemplate template;

    public CatalogDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Map<String, Object>> getQuantityCustomerLocation(String byCity) {
        String sql = "";
        if (byCity.equals(STRING_Y)) {
            sql = "SELECT  c.city as location_name,  COUNT(1)::integer as quantity";
            sql = sql + " FROM PUBLIC.customer AS ct";
            sql = sql + " INNER JOIN PUBLIC.address AS ad";
            sql = sql + " ON ct.address_id = ad.address_id";
            sql = sql + " INNER JOIN PUBLIC.city AS c";
            sql = sql + " ON c.city_id = ad.city_id";
            sql = sql + " WHERE ct.active = 1";
            sql = sql + " GROUP BY c.city";
            sql = sql + " ORDER BY c.city;";
        } else {
            sql = "SELECT  co.country as location_name,  COUNT(1)::integer as quantity";
            sql = sql + " FROM PUBLIC.customer AS ct";
            sql = sql + " INNER JOIN PUBLIC.address AS ad";
            sql = sql + " ON ct.address_id = ad.address_id";
            sql = sql + " INNER JOIN PUBLIC.city AS c";
            sql = sql + " ON c.city_id = ad.city_id";
            sql = sql + " INNER JOIN PUBLIC.country AS co";
            sql = sql + " ON co.country_id = c.country_id";
            sql = sql + " WHERE ct.active = 1";
            sql = sql + " GROUP BY  co.country";
            sql = sql + " ORDER BY co.country;";
        }

        return template.query(sql, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("locationName", resultSet.getString("location_name"));
            map.put("quantityCustomer", resultSet.getInt("quantity"));

            return map;
        });
    }

    @Override
    public List<Map<String, Object>> getActorInformation(String actor, String category) {
        category.replaceAll("[^a-zA-Z0-9]", "");
        actor.replaceAll("[^a-zA-Z]", "");

        String sql = "SELECT a.first_name, a.last_name, f.title, f.description, c.name AS category_name"
                + " FROM PUBLIC.actor AS a"
                + " INNER JOIN PUBLIC.film_actor AS fa"
                + " ON  fa.actor_id = a.actor_id"
                + " INNER JOIN PUBLIC.film AS f"
                + " ON  f.film_id = fa.film_id"
                + " INNER JOIN PUBLIC.film_category AS fc"
                + " ON fc.film_id =  f.film_id"
                + " INNER JOIN PUBLIC.category AS c"
                + " ON c.category_id = fc.category_id"
                + " WHERE  (a.first_name ILIKE  '%" + actor + "%'"
                + " OR a.last_name ILIKE  '%" + actor + "%')"
                + " ORDER BY a.first_name, a.last_name;";

        RowMapper<Map<String, Object>> mapper = (resultSet, i) -> {
            Map<String, Object> information = new HashMap<>();
            information.put("firstNameActor", resultSet.getString("first_name"));
            information.put("lastNameActor", resultSet.getString("last_name"));
            information.put("filmTitle", resultSet.getString("title"));
            information.put("filmDescription", resultSet.getString("description"));
            information.put("categoryName", resultSet.getString("category_name"));
            return information;
        };

        List<Map<String, Object>> info = template.query(sql, mapper);

        List<Map<String, Object>> infoFilteredByCategory = info.stream()
                .filter(map -> map.containsKey("categoryName") &&
                        map.get("categoryName").equals(category))
                .collect(Collectors.toList());

        return infoFilteredByCategory;
    }

}
