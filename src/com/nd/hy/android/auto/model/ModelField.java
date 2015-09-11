package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author liangbx
 * Date 2015/9/7
 */
public class ModelField {

    private final StringProperty dataType;
    private final StringProperty respFieldName;
    private final StringProperty genFieldName;
    private boolean isBaseField;
    private Model subModel;


    public ModelField() {
        this(null, null, null);
    }

    public ModelField(String dataType, String respFieldName, String genFieldName) {
        this.dataType = new SimpleStringProperty(dataType);
        this.respFieldName = new SimpleStringProperty(respFieldName);
        this.genFieldName = new SimpleStringProperty(genFieldName);
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

    public String getRespFieldName() {
        return respFieldName.get();
    }

    public StringProperty respFieldNameProperty() {
        return respFieldName;
    }

    public void setRespFieldName(String respFieldName) {
        this.respFieldName.set(respFieldName);
    }

    public String getGenFieldName() {
        return genFieldName.get();
    }

    public StringProperty genFieldNameProperty() {
        return genFieldName;
    }

    public void setGenFieldName(String genFieldName) {
        this.genFieldName.set(genFieldName);
    }

    public boolean isBaseField() {
        return isBaseField;
    }

    public void setIsBaseField(boolean isBaseField) {
        this.isBaseField = isBaseField;
    }

    public Model getSubModel() {
        return subModel;
    }

    public void setSubModel(Model subModel) {
        this.subModel = subModel;
    }

    private boolean hasModel() {
        if(subModel != null) {
            return true;
        }
        return false;
    }

    public boolean equals(ModelField field) {
        return getRespFieldName().equals(field.getRespFieldName());
    };

    public ModelField copy() {
        ModelField modelField = new ModelField(getDataType() + "", getRespFieldName() + "", getGenFieldName() + "");
        if(null != getSubModel()) {
            modelField.setSubModel(getSubModel().copy());
        }

        return modelField;
    };

}
