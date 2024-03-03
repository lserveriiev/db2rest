package com.homihq.db2rest.jdbc.rsql.operator.handler;

import com.homihq.db2rest.core.Dialect;
import com.homihq.db2rest.model.DbColumn;

import java.util.Map;

public class LessThanOperatorHandler implements OperatorHandler {

   private static final String OPERATOR = " < ";

    @Override
    public String handle(Dialect dialect, DbColumn columnName, String value, Class type, Map<String, Object> paramMap) {
        return columnName + OPERATOR + parseValue(value, type);
    }

}
