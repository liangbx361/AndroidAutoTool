package com.nd.hy.android.auto.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class HttpShowInfo {

    private final StringProperty method;
    private final StringProperty name;
    private final StringProperty description;
    private final BooleanProperty isGen;

    public HttpShowInfo(String method, String name, String description) {
        this.method = new SimpleStringProperty(method);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.isGen = new SimpleBooleanProperty(false);
    }

    public String getMethod() {
        return method.get();
    }

    public StringProperty methodProperty() {
        return method;
    }

    public void setMethod(String method) {
        this.method.set(method);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean getIsGen() {
        return isGen.get();
    }

    public BooleanProperty isGenProperty() {
        return isGen;
    }

    public void setIsGen(boolean isGen) {
        this.isGen.set(isGen);
    }
}
