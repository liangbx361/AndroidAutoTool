package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/8
 * DESC 请求Bean定义
 */
public class Request {

    private final StringProperty reqName;
    private final StringProperty reqPath;
    private final StringProperty reqMethod;
    private final StringProperty reqFnName;
    private final StringProperty reqDescription;

    private List<RequestParam> requestParamList;

    public Request() {
        this(null, null, null, null, null);
    }

    public Request(String reqName, String reqPath, String reqMethod, String reqFnName, String reqDescription) {
        this.reqName = new SimpleStringProperty(reqName);
        this.reqPath = new SimpleStringProperty(reqPath);
        this.reqMethod = new SimpleStringProperty(reqMethod);
        this.reqFnName = new SimpleStringProperty(reqFnName);
        this.reqDescription = new SimpleStringProperty(reqDescription);
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

    public List<RequestParam> getRequestParamList() {
        return requestParamList;
    }

    public void setRequestParamList(List<RequestParam> requestParamList) {
        this.requestParamList = requestParamList;
    }

    public Request copy() {
        Request request = new Request();
        request.setReqName(getReqName() + "");
        request.setReqPath(getReqPath() + "");
        request.setReqMethod(getReqMethod() + "");
        request.setReqFnName(getReqMethod() + "");
        request.setReqDescription(getReqDescription() + "");

        List<RequestParam> newReqParam = new ArrayList<>();
        request.setRequestParamList(newReqParam);
        for(RequestParam requestParam : requestParamList) {
            newReqParam.add(requestParam.copy());
        }
        return request;
    }
}
