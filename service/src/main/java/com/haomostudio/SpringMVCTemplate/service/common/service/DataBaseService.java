package com.haomostudio.SpringMVCTemplate.service.common.service;





import com.haomostudio.SpringMVCTemplate.common.domain.Column;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface DataBaseService {

    boolean createTable(String tableNamePrefix, String tableName, List<Column> columns);

    boolean dropTable(String tableNamePrefix, String tableName);

    boolean renameTable(String tableNamePrefix, String oldName, String newName);

    boolean addColumn(String tableNamePrefix, String tableName, Column column);

    boolean dropColumn(String tableNamePrefix, String tableName, String columnName);

    boolean changeColumn(String tableNamePrefix, String tableName, String columnName, Column column);

    boolean executeSQL(String sql);

    List<Map<String, Object>> getTableData(String tableNamePrefix, String tableName, Map<String, Object> params);

    int getTableDataCount(String tableNamePrefix, String tableName, Map<String, Object> params);

    int getColumnValueHasValueCount(String tableNamePrefix, String tableName, String columnName, Integer type);

    int getColumnValueNotNullCount(String tableNamePrefix, String tableName, String columnName);

    int getColumnValueNotZeroCount(String tableNamePrefix, String tableName, String columnName);

    boolean executeSQL(String sql, List<Object> params);

    int update(String tableNamePrefix, String tableName, Map<String, Object> map);

    int insert(String tableNamePrefix, String tableName, Map<String, Object> map);

    int inserts(String tableNamePrefix, String tableName, List<Map<String, Object>> maps);

    int delete(String tableNamePrefix, String tableName, Map<String, Object> map);

    String convertParam2SqlConnByAndForUser(String sortItem, String sortOrder, String filters);

    String convertParam2SqlConnByAnd(String sortItem, String sortOrder, String filters);

    String convertParam2SqlConnByWhereForUser(String sortItem, String sortOrder, String filters);

    String convertParam2SqlConnByWhere(String sortItem, String sortOrder, String filters);

    String convertParam2SqlConnByAnd(String filters);

    String convertParam2SqlConnByWhere(String filters);

    Map<String, Object> executeQuerySQL(String sql);

    List<Map<String, Object>> zxexecuteListSQL(String sql);

    Integer zxqueryForInt(String sql);

    void executeSingleSQL(String sql);


}
