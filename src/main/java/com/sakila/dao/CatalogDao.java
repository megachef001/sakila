package com.sakila.dao;

import java.util.List;
import java.util.Map;

//import javax.swing.text.StyledEditorKit.BoldAction;

public interface CatalogDao {
    List<Map<String, Object>> getQuantityCustomerLocation(String byCity);

    List<Map<String, Object>> getActorInformation(String actor, String category);

}
