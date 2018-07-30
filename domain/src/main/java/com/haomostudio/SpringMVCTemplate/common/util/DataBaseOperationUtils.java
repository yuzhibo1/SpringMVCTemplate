package com.haomostudio.SpringMVCTemplate.common.util;

import com.alibaba.fastjson.JSONObject;
import com.haomostudio.SpringMVCTemplate.common.domain.Column;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBaseOperationUtils {

    public static final String DB_TYPE_ORACLE = "oracle";

    public static final String DB_TYPE_MYSQL = "mysql";

    public static final String DATA_TYPE_VARCHAR = "varchar";

    public static final String DATA_TYPE_TIMESTAMP = "timestamp";

    public static final String DATA_TYPE_TEXT = "text";

    public static final String DATA_TYPE_INT = "int";

    public static final String DATA_TYPE_FLOAT = "float";

    public static final String DATA_TYPE_DOUBLE = "double";

    public static List<String> getPropertyName(Object obj) {
        ArrayList list = new ArrayList();
        Field[] fs = obj.getClass().getDeclaredFields();
        Field[] var7 = fs;
        int var6 = fs.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            Field f = var7[var5];
            list.add(f.getName());
        }

        return list;
    }

    public static Object[] GetObjectArgs(Map<String, Object> map) {

        return map.values().toArray();
    }

    public static String GetNames(Map<String, Object> map, String operation, String dbType) {
        String columns = "";

        try {
            if("INSERT".equals(operation)) {
                String params = "(";
                columns = "(";

                for(String key : map.keySet()) {
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)?"\"" + key + "\",":"`" + key + "`,");
                    params = params + "?,";
                }

                if(!"(".equals(columns)) {
                    columns = columns.substring(0, columns.length() - 1);
                }

                if(!"(".equals(params)) {
                    params = params.substring(0, params.length() - 1);
                }

                columns = columns + ")";
                params = params + ")";
                return columns + " VALUES" + params;
            }

            if("UPDATE".equals(operation)) {
                for(String key : map.keySet()) {
                    if ("ID".equals(key)) {
                        continue;
                    }
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)?"\"" + key + "\"=?,":"`" + key + "`=?,");
                }

                if(columns.substring(columns.length() - 1, columns.length()).equals(",")) {
                    columns = columns.substring(0, columns.length() - 1);
                }

                columns = columns + " WHERE ID=?";
            }

            if ("DELETE".equals(operation)) {
                for(String key : map.keySet()) {
                    columns = columns + (DB_TYPE_ORACLE.equals(dbType)?"\"" + key + "\"=? AND ":"`" + key + "`=? AND ");
                }

                if(columns.endsWith(" AND ")) {
                    columns = columns.substring(0, columns.length() - 5);
                }

            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return columns;
    }


    public static String getDataType(Column tc) {
        if (tc.getLength() != 0) {
            return tc.getDataType() + "(" + tc.getLength() + ")";
        } else {
            return tc.getDataType();
        }
    }

    public static String getIsNull(Column tc) {
        if (tc.isKey() && tc.isAutoIncrement()) {
            return "NOT NULL AUTO_INCREMENT";
        }
        if ("timestamp".equals(tc.getDataType())) {
            if(tc.getName().equals(Constants.COLUMN_CREATE_TIME)){
                return "DEFAULT CURRENT_TIMESTAMP";
            }else{
                return "NULL DEFAULT NULL";
            }
        }
        if (tc.isNull()) {
            return "DEFAULT NULL";
        } else {
            return "NOT NULL";
        }
    }

    public static String getPrimaryKey(List<Column> ltc) {
        String pk = "PRIMARY KEY (";
        for (Column tc : ltc) {
            if (tc.isKey()) {
                pk += "`" + tc.getName() + "`,";
            }
        }
        if ("PRIMARY KEY (".equals(pk)) {
            return "";
        } else {
            return pk.substring(0, pk.length() - 1) + ")";
        }
    }


    public static String getInSQL(String params, String flag) {
        String tempStr = params.replace(flag, ",");
        String[] tempStrArr = tempStr.split(",");
        String InSQL = "";

        for(int i = 0; i < tempStrArr.length; ++i) {
            InSQL = InSQL + "?,";
        }

        if(!"".equals(InSQL)) {
            InSQL = InSQL.substring(0, InSQL.length() - 1);
        }

        return InSQL;
    }

    public static List<Object> getInArgs(String params, String flag, String dataType) {
        String tempStr = params.replace(flag, ",");
        String[] tempStrArr = tempStr.split(",");
        ArrayList obj = new ArrayList();
        int i;
        if("varchar".equalsIgnoreCase(dataType) || "varchar2".equalsIgnoreCase(dataType) || "blob".equalsIgnoreCase(dataType)) {
            for(i = 0; i < tempStrArr.length; ++i) {
                obj.add(tempStrArr[i]);
            }
        } else if("integer".equals(dataType)) {
            for(i = 0; i < tempStrArr.length; ++i) {
                obj.add(Integer.valueOf(Integer.parseInt(tempStrArr[i])));
            }
        }

        return obj;
    }


    /**
     *
     * Get Column
     *
     * additionals JSON格式
     * {
     'type': 1,      // 字段类型
     'options': [],  // 可选项
     'constraint': { // 字段约束
     'text_length': 32, // 对于文本类型有效
     'decimal': {
     'is_currency': false, // 是否是货币
     'decimal_place': 2    // 小数点位数
     }
     }
     }
     *
     * @param columnName
     * @param additionals
     * @return
     */
    public static Column getColumn(String columnName, String additionals) {

        Column column = new Column();
        column.setName(columnName);
        if (null == additionals) {
            column.setDataType(DATA_TYPE_VARCHAR);
            column.setLength(128);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(additionals);
            String type = getDataType(jsonObject.getInteger("type"));
            column.setDataType(type);
            JSONObject constraint = jsonObject.getJSONObject("constraint");
            if (DATA_TYPE_VARCHAR.equals(type) || DATA_TYPE_TEXT.equals(type)) {
                Integer textLength = constraint.getInteger("text_length");
                column.setLength(textLength);
            } else if (DATA_TYPE_FLOAT.equals(type) || DATA_TYPE_DOUBLE.equals(type)) {
                JSONObject decimal = constraint.getJSONObject("decimal");
                Boolean isCurrency = decimal.getBoolean("is_currency");
                Integer decimalPlace = decimal.getInteger("decimal_place");
            }
        }
        if (Constants.COLUMN_ID.equals(columnName)) {
            column.setIsKey(true);
        }
        if (Constants.COLUMN_ID.equals(columnName) || Constants.COLUMN_CREATE_TIME.equals(columnName) || Constants.COLUMN_FORM_INSTANCE_ID.equals(columnName)) {
            column.setIsNull(false);
        } else {
            column.setIsNull(true);
        }
        return column;
    }


    private static String getDataType(int type) {

        switch (type) {
            case 1:
                return DATA_TYPE_TEXT;
            case 2:
                return DATA_TYPE_VARCHAR;
            case 3:
                return DATA_TYPE_TIMESTAMP;
            case 4:
                return DATA_TYPE_TIMESTAMP;
            case 5:
                return DATA_TYPE_VARCHAR;
            case 6:
                return DATA_TYPE_VARCHAR;
            case 7:
                return DATA_TYPE_VARCHAR;
            case 8:
                return DATA_TYPE_VARCHAR;
            case 9:
                return DATA_TYPE_INT;
            case 10:
                return DATA_TYPE_DOUBLE;
            case 11:
                return DATA_TYPE_VARCHAR;
            case 12:
                return DATA_TYPE_VARCHAR;
            default:
                return DATA_TYPE_VARCHAR;
        }
    }


    public static String getMySQLTableName(String prefix, String tableName) {

        return "`" + getFinalTableName(prefix, tableName) + "`";
    }

    public static String getOracleTableName(String prefix, String tableName) {
        return "\"" + getFinalTableName(prefix, tableName) + "\"";
    }

    private static String getFinalTableName(String prefix, String tableName) {
        String finalName = "";
        if (!StringUtils.isEmpty(prefix)) {
            finalName += prefix.trim();
            finalName += tableName.trim().toUpperCase();
        } else {
            finalName += tableName.trim().toLowerCase();
        }
        return finalName;
    }
}
