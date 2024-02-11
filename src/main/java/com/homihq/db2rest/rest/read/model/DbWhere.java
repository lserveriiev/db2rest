package com.homihq.db2rest.rest.read.model;


import java.util.List;
import java.util.Map;

public record DbWhere(String tableName, DbTable table, List<DbColumn> columns, Map<String,Object> paramMap) { }
