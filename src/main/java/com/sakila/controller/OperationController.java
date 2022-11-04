package com.sakila.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sakila.dao.OperationDao;

//import com.sakila.exceptionhandling.ResourceNotFoundException;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sakila/operation")
public class OperationController {

    @Autowired
    OperationDao service;

    @ResponseBody
    @GetMapping(value = "/getOverdueRental", produces = "application/json")
    public List<Map<String, Object>> getOverdueRental() {

        return service.getOverdueRental();
    }

}
