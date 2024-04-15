package com.homihq.db2rest.mongo.rest.api;

import com.homihq.db2rest.core.dto.CreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface MongoRestApi {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{collectionName}")
    CreateResponse save(@PathVariable String collectionName,
                        @RequestParam(name = "fields", required = false) List<String> includeFields,
                        @RequestBody Map<String, Object> data);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{collectionName}")
    public Object findAll(@PathVariable String collectionName,
                          @RequestParam(name = "fields", required = false, defaultValue = "*") String fields,
                          @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                          @RequestParam(name = "sort", required = false, defaultValue = "") List<String> sorts,
                          @RequestParam(name = "limit", required = false, defaultValue = "-1") int limit,
                          @RequestParam(name = "offset", required = false, defaultValue = "-1") long offset);
}