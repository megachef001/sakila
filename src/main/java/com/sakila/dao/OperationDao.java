package com.sakila.dao;

import java.util.List;
import java.util.Map;

public interface OperationDao {

    List<Map<String, Object>> getOverdueRental();
}
