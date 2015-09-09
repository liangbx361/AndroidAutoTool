package com.nd.hy.android.auto.model;

import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/9
 */
public class Project {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 代码生成路径
     */
    private String genPath;

    /**
     * http 信息列表
     */
    private List<HttpInfo> httpInfoList;

    /**
     * baseModel
     */
    private Model baseModel;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getGenPath() {
        return genPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public List<HttpInfo> getHttpInfoList() {
        return httpInfoList;
    }

    public void setHttpInfoList(List<HttpInfo> httpInfoList) {
        this.httpInfoList = httpInfoList;
    }

    public Model getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(Model baseModel) {
        this.baseModel = baseModel;
    }
}
