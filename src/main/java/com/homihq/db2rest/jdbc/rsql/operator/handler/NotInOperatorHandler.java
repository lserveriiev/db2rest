package com.homihq.db2rest.jdbc.rsql.operator.handler;

import com.homihq.db2rest.jdbc.core.model.DbWhere;
import com.homihq.db2rest.jdbc.dialect.Dialect;
import com.homihq.db2rest.jdbc.core.model.DbColumn;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class NotInOperatorHandler implements OperatorHandler {

   private static final String OPERATOR = " not in ";

    @Override
    public String handle(Dialect dialect, DbColumn columnName, DbWhere dbWhere, String value, Class type, Map<String, Object> paramMap) {
        return handle(dialect, columnName, dbWhere, Arrays.asList(value), type, paramMap);
    }

    @Override
    public String handle(Dialect dialect, DbColumn column, DbWhere dbWhere, List<String> values, Class type, Map<String, Object> paramMap) {

        List<Object> vo = dialect.parseListValues(values, type);

        if(dialect.supportAlias()) {

            String key = reviewAndSetParam(dialect.getAliasedNameParam(column, dbWhere.isDelete()), vo, paramMap);
            return dialect.getAliasedName(column, dbWhere.isDelete()) + OPERATOR +" ( "+ PREFIX + key + " ) ";
        }
        else{
            String key = reviewAndSetParam(column.name(), vo, paramMap);
            return column.name() + OPERATOR + " ( " + PREFIX + key + " ) ";
        }
    }

}
