package com.haomostudio.SpringMVCTemplate.common.domain;

/**
 *
 */
public class Column {

    private String name;
    private String dataType;
    private int length;
    private boolean isNull;
    private boolean isKey;
    private boolean autoIncrement;
    private Integer decimalPlace;

    public Integer getDecimalPlace() { return decimalPlace; }

    public void setDecimalPlace(Integer decimalPlace) { this.decimalPlace = decimalPlace; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setIsKey(boolean isKey) {
        this.isKey = isKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
}
