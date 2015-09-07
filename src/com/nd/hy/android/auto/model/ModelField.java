package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author liangbx
 * Date 2015/9/7
 */
public class ModelField {

    private final StringProperty dataType;
    private final StringProperty respFieldsName;
    private final StringProperty genFieldsName;

    public ModelField(String dataType, String respFieldsName, String genFieldsName) {
        this.dataType = new SimpleStringProperty(dataType);
        this.respFieldsName = new SimpleStringProperty(respFieldsName);
        this.genFieldsName = new SimpleStringProperty(genFieldsName);
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

    public String getRespFieldsName() {
        return respFieldsName.get();
    }

    public StringProperty respFieldsNameProperty() {
        return respFieldsName;
    }

    public void setRespFieldsName(String respFieldsName) {
        this.respFieldsName.set(respFieldsName);
    }

    public String getGenFieldsName() {
        return genFieldsName.get();
    }

    public StringProperty genFieldsNameProperty() {
        return genFieldsName;
    }

    public void setGenFieldsName(String genFieldsName) {
        this.genFieldsName.set(genFieldsName);
    }
}
