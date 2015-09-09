package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author liangbx
 * Date 2015/9/9
 * DESC 请求参数定义
 */
public class RequestParam {

    /**
     * 在Url请求中的使用方式
     */
    private final StringProperty typeForUrl;

    /**
     * 数据类型
     */
    private final StringProperty dataType;

    /**
     * 在url中使用的名称
     */
    private final StringProperty nameForUrl;

    /**
     * 在函数中使用的名称
     */
    private final StringProperty nameForFn;

    public RequestParam() {
        this(null, null, null, null);
    }

    public RequestParam(String typeForUrl, String dataType, String nameForUrl, String nameForFn) {
        this.typeForUrl = new SimpleStringProperty(typeForUrl);
        this.dataType = new SimpleStringProperty(dataType);
        this.nameForUrl = new SimpleStringProperty(nameForUrl);
        this.nameForFn = new SimpleStringProperty(nameForFn);
    }

    public String getTypeForUrl() {
        return typeForUrl.get();
    }

    public StringProperty typeForUrlProperty() {
        return typeForUrl;
    }

    public void setTypeForUrl(String typeForUrl) {
        this.typeForUrl.set(typeForUrl);
    }

    public String getDataType() {
        return dataType.get();
    }

    public StringProperty dataTypeProperty() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }

    public String getNameForUrl() {
        return nameForUrl.get();
    }

    public StringProperty nameForUrlProperty() {
        return nameForUrl;
    }

    public void setNameForUrl(String nameForUrl) {
        this.nameForUrl.set(nameForUrl);
    }

    public String getNameForFn() {
        return nameForFn.get();
    }

    public StringProperty nameForFnProperty() {
        return nameForFn;
    }

    public void setNameForFn(String nameForFn) {
        this.nameForFn.set(nameForFn);
    }
}
