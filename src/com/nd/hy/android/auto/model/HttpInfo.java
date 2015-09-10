package com.nd.hy.android.auto.model;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Author liangbx
 * Date 2015/9/8
 */
public class HttpInfo {

    private Request request;

    private Response response;

    //生成标志
    private final SimpleBooleanProperty genFlag;

    public HttpInfo() {
        this(true);
    }

    public HttpInfo(boolean genFlag) {
        this.genFlag = new SimpleBooleanProperty(genFlag);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean getGenFlag() {
        return genFlag.get();
    }

    public SimpleBooleanProperty genFlagProperty() {
        return genFlag;
    }

    public void setGenFlag(boolean genFlag) {
        this.genFlag.set(genFlag);
    }

    public HttpInfo copy() {
        HttpInfo httpInfo = new HttpInfo();

        return httpInfo;
    }
}
