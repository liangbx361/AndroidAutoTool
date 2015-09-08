package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author liangbx
 * Date 2015/9/8
 */
public class Request {

    private final StringProperty reqName;
    private final StringProperty reqPath;
    private final StringProperty reqMethod;
    private final StringProperty reqParams;
    private final StringProperty reqFnName;
    private final StringProperty reqDescription;

    public Request() {
        this(null, null, null, null, null, null);
    }

    public Request(StringProperty reqName, StringProperty reqPath, StringProperty reqMethod, StringProperty reqParams,
                   StringProperty reqFnName, StringProperty reqDescription) {
        this.reqName = reqName;
        this.reqPath = reqPath;
        this.reqMethod = reqMethod;
        this.reqParams = reqParams;
        this.reqFnName = reqFnName;
        this.reqDescription = reqDescription;
    }

    public String getReqName() {
        return reqName.get();
    }

    public StringProperty reqNameProperty() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName.set(reqName);
    }

    public String getReqPath() {
        return reqPath.get();
    }

    public StringProperty reqPathProperty() {
        return reqPath;
    }

    public void setReqPath(String reqPath) {
        this.reqPath.set(reqPath);
    }

    public String getReqMethod() {
        return reqMethod.get();
    }

    public StringProperty reqMethodProperty() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod.set(reqMethod);
    }

    public String getReqParams() {
        return reqParams.get();
    }

    public StringProperty reqParamsProperty() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams.set(reqParams);
    }

    public String getReqFnName() {
        return reqFnName.get();
    }

    public StringProperty reqFnNameProperty() {
        return reqFnName;
    }

    public void setReqFnName(String reqFnName) {
        this.reqFnName.set(reqFnName);
    }

    public String getReqDescription() {
        return reqDescription.get();
    }

    public StringProperty reqDescriptionProperty() {
        return reqDescription;
    }

    public void setReqDescription(String reqDescription) {
        this.reqDescription.set(reqDescription);
    }
}
