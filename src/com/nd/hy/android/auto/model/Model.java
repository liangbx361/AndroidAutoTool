package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author liangbx
 * Date 2015/9/9
 */
public class Model {

    private final StringProperty modelName;

    private Set<String> importList;

    private List<ModelField> modelFieldList;

    private boolean isBaseModel;

    private boolean listFlag = false;

    public Model() {
        this(null);
    }

    public Model(String modelName) {
        this.modelName = new SimpleStringProperty(modelName);
    }

    public String getModelName() {
        return modelName.get();
    }

    public StringProperty modelNameProperty() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName.set(modelName);
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public List<ModelField> getModelFieldList() {
        return modelFieldList;
    }

    public void setModelFieldList(List<ModelField> modelFieldList) {
        this.modelFieldList = modelFieldList;
    }

    public boolean isBaseModel() {
        return isBaseModel;
    }

    public void setIsBaseModel(boolean isBaseModel) {
        this.isBaseModel = isBaseModel;
    }

    public boolean isListFlag() {
        return listFlag;
    }

    public void setListFlag(boolean listFlag) {
        this.listFlag = listFlag;
    }

    public Model copy() {
        Model model = new Model(getModelName()+"");

        Set<String> newImportList = new HashSet<>();
        for(String s : importList) {
            newImportList.add(s+"");
        }
        model.setImportList(newImportList);

        List<ModelField> newModelFieldList = new ArrayList<>();
        for(ModelField modelField : modelFieldList) {
            newModelFieldList.add(modelField.copy());
        }
        model.setModelFieldList(newModelFieldList);

        return model;
    }
}
