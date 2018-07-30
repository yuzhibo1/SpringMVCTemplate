package com.haomostudio.SpringMVCTemplate.service.common.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haomostudio.SpringMVCTemplate.common.domain.Column;
import com.haomostudio.SpringMVCTemplate.common.util.DataBaseOperationUtils;
import com.haomostudio.SpringMVCTemplate.dao.impl.DataBaseDao;
import com.haomostudio.SpringMVCTemplate.service.common.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service(value = "databaseService")
public class OracleDataBaseServiceImpl implements DataBaseService {

    @Autowired
    private DataBaseDao databaseDao;

    public static final String EQUALS = "equals";

    public static final String RANGE = "range";

    public static final String START = "start";

    public static final String END = "end";

    public static final String LIKE = "like";

    public static final String IN = "in";

    @Override
    public boolean createTable(String tableNamePrefix, String tableName, List<Column> columns) {
        String sql = "CREATE TABLE "
                + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " (";
        for (Column tc : columns) {
            sql += tc.getName().toUpperCase() + " " + DataBaseOperationUtils.getDataType(tc) + " "
                    + DataBaseOperationUtils.getIsNull(tc) + ",";
        }
        if ("".equals(DataBaseOperationUtils.getPrimaryKey(columns))) {
            sql = sql.substring(0, sql.length() - 1);
        }
        sql += DataBaseOperationUtils.getPrimaryKey(columns) + ");";
        return executeSQL(sql);
    }

    @Override
    public boolean dropTable(String tableNamePrefix, String tableName) {
        String sql = "DROP TABLE " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName);
        return executeSQL(sql);
    }

    @Override
    public boolean renameTable(String tableNamePrefix, String oldName, String newName) {
        String sql = "ALTER TABLE " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, oldName) + " RENAME TO "
                + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, newName);
        return executeSQL(sql);
    }

    @Override
    public boolean addColumn(String tableNamePrefix, String tableName, Column column) {
        String sql = "ALTER TABLE " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " ADD " + column.getName().toUpperCase() + " "
                + DataBaseOperationUtils.getDataType(column) + " "
                + DataBaseOperationUtils.getIsNull(column) + ";";
        return executeSQL(sql);
    }

    @Override
    public boolean dropColumn(String tableNamePrefix, String tableName, String columnName) {
        String sql = "ALTER TABLE " + DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName) + " DROP COLUMN " + columnName.toUpperCase() + ";";
        return executeSQL(sql);
    }

    @Override
    public boolean changeColumn(String tableNamePrefix, String tableName, String columnName, Column column) {
        String newColumnName = column.getName().toUpperCase();
        String orclTableName = DataBaseOperationUtils.getOracleTableName(tableNamePrefix, tableName);
        String sql = "";
        if (!columnName.equalsIgnoreCase(newColumnName)) {
            sql = "ALTER TABLE " + orclTableName + " RENAME COLUMN " + columnName.toUpperCase() + " TO " + newColumnName + ";";
        }
        sql += "ALTER TABLE " + orclTableName + " MODIFY " + newColumnName + " " + DataBaseOperationUtils.getDataType(column) + " "
                + DataBaseOperationUtils.getIsNull(column) + ";";
        return executeSQL(sql);
    }

    @Override
    public boolean executeSQL(String sql) {
        return this.databaseDao.executeSQL(sql);
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableNamePrefix, String tableName, Map<String, Object> params) {
        return this.databaseDao.getTableData(tableNamePrefix, tableName, params);
    }

    @Override
    public int getTableDataCount(String tableNamePrefix, String tableName, Map<String, Object> params) {
        return this.databaseDao.getTableDataCount(tableNamePrefix, tableName, params);
    }


    @Override
    public int getColumnValueHasValueCount(String tableNamePrefix, String tableName, String columnName, Integer type) {
        //9：整形   10：浮点型
        if (9 == type || 10 == type) {
            return this.databaseDao.getColumnNotZeroCount(tableNamePrefix, tableName, columnName);
        }
        return this.databaseDao.getColumnNotNullCount(tableNamePrefix, tableName, columnName);
    }


    @Override
    public int getColumnValueNotNullCount(String tableNamePrefix, String tableName, String columnName) {
        return this.databaseDao.getColumnNotNullCount(tableNamePrefix, tableName, columnName);
    }

    @Override
    public int getColumnValueNotZeroCount(String tableNamePrefix, String tableName, String columnName) {
        return this.databaseDao.getColumnNotZeroCount(tableNamePrefix, tableName, columnName);
    }

    @Override
    public boolean executeSQL(String sql, List<Object> params) {
        return this.databaseDao.executeSQL(sql, params);
    }

    @Override
    public int update(String tableNamePrefix, String tableName, Map<String, Object> map) {
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        if (null != map) {
            linkedHashMap.putAll(map);
        }
        return this.databaseDao.update(tableNamePrefix, tableName, linkedHashMap);
    }

    @Override
    public int insert(String tableNamePrefix, String tableName, Map<String, Object> map) {
        return this.databaseDao.insert(tableNamePrefix, tableName, map);
    }

    @Override
    public int inserts(String tableNamePrefix, String tableName, List<Map<String, Object>> maps) {
        return this.databaseDao.inserts(tableNamePrefix, tableName, maps);
    }

    @Override
    public int delete(String tableNamePrefix, String tableName, Map<String, Object> map) {
        return this.databaseDao.delete(tableNamePrefix, tableName, map);
    }

    @Override
    /**
     * 多表联合查询问题
     */
    public String convertParam2SqlConnByAnd(String sortItem, String sortOrder,
                                            String filters) {
        StringBuffer sql = new StringBuffer();

        if (!StringUtils.isEmpty(filters)) {
            try {
                filters = URLDecoder.decode(filters, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject filtersJsonObj = JSONObject.parseObject(filters);
            for (String tableName : filtersJsonObj.keySet()) {
                JSONObject conditionsJsonObj = filtersJsonObj.getJSONObject(tableName);
                for (String columnName : conditionsJsonObj.keySet()) {
                    JSONObject columnJsonObj = conditionsJsonObj.getJSONObject(columnName);
                    for (String operationName : columnJsonObj.keySet()) {
                        sql.append(" AND ");
                        String column = tableName + "." + columnName;
                        if (RANGE.equals(operationName)) {
                            JSONObject rangeJsonObj = columnJsonObj.getJSONObject(operationName);
                            String start = rangeJsonObj.getString(START);
                            String end = rangeJsonObj.getString(END);
                            sql.append("(").append(column).append(">").append("'").append(start).append("'").
                                    append(" AND ").append(column).append("<").append("'").append(end).append("'").append(")");
                        } else if (LIKE.equals(operationName)) {
                            String value = columnJsonObj.getString(LIKE);
                            sql.append("(").append(column).append(" like '%").append(value).append("%')");
                        } else if("notLike".equals(operationName)){
                            String value = columnJsonObj.getString("notLike");
                            sql.append("(").append(column).append(" not like '").append(value).append("')");
                        } else if (EQUALS.equals(operationName)) {
                            // 保持兼容性的成长
                            List<String> valueArray = new ArrayList<>();
                            try {
                                JSONArray jsonArray = columnJsonObj.getJSONArray(EQUALS);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    String v = jsonArray.getString(i);
                                    valueArray.add(v);
                                }
                            } catch (Exception e) {
                                String values = columnJsonObj.getString(EQUALS);
                                for (String s : values.split(",")) {
                                    valueArray.add(s);
                                }
                            }
                            sql.append("(");
                            for (int i = 0; i < valueArray.size(); i++) {
                                String value = valueArray.get(i);
                                if (i != 0) {
                                    sql.append(" OR ");
                                }
                                sql.append(column).append("=").append("'").append(value).append("'");
                            }
                            sql.append(")");
                        } else if(IN.equals(operationName)){
                            JSONArray values = columnJsonObj.getJSONArray(IN);
                            sql.append("(").append(column).append(" in ('")
                                    .append(org.apache.commons.lang3.StringUtils.join(
                                            values.stream().map(value -> (String)value).collect(Collectors.toList()),
                                            "', '"
                                    )).append("'))");
                        }
                    }
                }
            }
        }

        if (!StringUtils.isEmpty(sortItem) && !StringUtils.isEmpty(sortOrder)) {
            // 排序
            String[] sorts = sortItem.split(",");
            String[] orders = sortOrder.split(",");
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (i = 0; i < sorts.length - 1; i++) {
                sb.append(" " + sorts[i]);
                if (i < orders.length) {
                    sb.append(" " + orders[i]);
                }
                sb.append(" , ");
            }
            sb.append(" " + sorts[i]);
            if (i < orders.length) {
                sb.append(" " + orders[i]);
            }
//            sql.append(" order by ").append(sortItem).append(" ").append(sortOrder);
            sql.append(" order by ").append(sb.toString());
        }


        return sql.toString();
    }

    @Override
    /**
     * 多表联合查询问题
     */
    public String convertParam2SqlConnByAndForUser(String sortItem, String sortOrder,
                                                   String filters) {
        StringBuffer sql = new StringBuffer();

        if (!StringUtils.isEmpty(filters)) {
            try {
                filters = URLDecoder.decode(filters, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject filtersJsonObj = JSONObject.parseObject(filters);
            for (String tableName : filtersJsonObj.keySet()) {
                JSONObject conditionsJsonObj = filtersJsonObj.getJSONObject(tableName);
                for (String columnName : conditionsJsonObj.keySet()) {
                    JSONObject columnJsonObj = conditionsJsonObj.getJSONObject(columnName);
                    for (String operationName : columnJsonObj.keySet()) {
                        sql.append(" AND ");
                        String column = tableName + "." + columnName;
                        if (RANGE.equals(operationName)) {
                            JSONObject rangeJsonObj = columnJsonObj.getJSONObject(operationName);
                            String start = rangeJsonObj.getString(START);
                            String end = rangeJsonObj.getString(END);
                            sql.append("(").append(column).append(">").append("'").append(start).append("'").
                                    append(" AND ").append(column).append("<").append("'").append(end).append("'").append(")");
                        } else if (LIKE.equals(operationName)) {
                            String value = columnJsonObj.getString(LIKE);
                            if (column.equals("crm_user.username")) {
                                sql.append("((crm_user.username like '%" + value + "%') or (crm_user.loginid like '%" + value + "%'))");
                            } else {
                                sql.append("(").append(column).append(" like '%").append(value).append("%')");
                            }
                        } else if (EQUALS.equals(operationName)) {
                            // 保持兼容性的成长
                            List<String> valueArray = new ArrayList<>();
                            try {
                                JSONArray jsonArray = columnJsonObj.getJSONArray(EQUALS);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    String v = jsonArray.getString(i);
                                    valueArray.add(v);
                                }
                            } catch (Exception e) {
                                String values = columnJsonObj.getString(EQUALS);
                                for (String s : values.split(",")) {
                                    valueArray.add(s);
                                }
                            }
                            sql.append("(");
                            for (int i = 0; i < valueArray.size(); i++) {
                                String value = valueArray.get(i);
                                if (i != 0) {
                                    sql.append(" OR ");
                                }
                                sql.append(column).append("=").append("'").append(value).append("'");
                            }
                            sql.append(")");
                        }
                    }
                }
            }
        }

        if (!StringUtils.isEmpty(sortItem) && !StringUtils.isEmpty(sortOrder)) {
//            sql.append(" order by ").append(sortItem).append(" ").append(sortOrder);
            // 排序
            String[] sorts = sortItem.split(",");
            String[] orders = sortOrder.split(",");
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (i = 0; i < sorts.length - 1; i++) {
                sb.append(" " + sorts[i]);
                if (i < orders.length) {
                    sb.append(" " + orders[i]);
                }
                sb.append(" , ");
            }
            sb.append(" " + sorts[i]);
            if (i < orders.length) {
                sb.append(" " + orders[i]);
            }
//            sql.append(" order by ").append(sortItem).append(" ").append(sortOrder);
            sql.append(" order by ").append(sb.toString());
        }


        return sql.toString();
    }

    @Override
    public String convertParam2SqlConnByWhere(String sortItem, String sortOrder, String filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" WHERE 1=1 ").append(convertParam2SqlConnByAnd(sortItem, sortOrder, filters));
        return sql.toString();
    }

    @Override
    public String convertParam2SqlConnByWhereForUser(String sortItem, String sortOrder, String filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" WHERE 1=1 ").append(convertParam2SqlConnByAndForUser(sortItem, sortOrder, filters));
        return sql.toString();
    }

    @Override
    public String convertParam2SqlConnByAnd(String filters) {
        return convertParam2SqlConnByAnd(null, null, filters);
    }

    @Override
    public String convertParam2SqlConnByWhere(String filters) {
        return convertParam2SqlConnByWhere(null, null, filters);
    }

    @Override
    public Map<String, Object> executeQuerySQL(String sql) {
        return this.databaseDao.executeQuerySQL(sql);
    }

    @Override
    public List<Map<String, Object>> zxexecuteListSQL(String sql) {
        return this.databaseDao.executeListSQL(sql);
    }

    @Override
    public Integer zxqueryForInt(String sql) {
        return this.databaseDao.queryForInt(sql, null);
    }

    @Override
    public void executeSingleSQL(String sql) {
        this.databaseDao.executeSingleSQL(sql);
    }
}
