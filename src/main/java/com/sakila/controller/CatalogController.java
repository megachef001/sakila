package com.sakila.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sakila.dao.CatalogDao;

//import com.sakila.exceptionhandling.ResourceNotFoundException;

//import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sakila/catalog")
public class CatalogController {

    @Autowired
    CatalogDao service;

    @ResponseBody
    @GetMapping(value = "/getQuantityCustomerLocation", produces = "application/json")
    public List<Map<String, Object>> getQuantityCustomerLocation(
            @RequestParam("byCity") String byCity) {

        return service.getQuantityCustomerLocation(byCity);
    }

    @ResponseBody
    @GetMapping(value = "/getActorInformation", produces = "application/json")
    public List<Map<String, Object>> getActorInformation(
            @RequestParam("actor") String actor, @RequestParam("category") String category) {

        return service.getActorInformation(actor, category);
    }

}
