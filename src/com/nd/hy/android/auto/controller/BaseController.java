package com.nd.hy.android.auto.controller;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/10
 */
public abstract class BaseController<T> implements Initializable {

    private Stage stage;

    private T model;
    private T backModel;
    private Object parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    protected abstract void initView();

    protected abstract void fillView(T model);

    protected abstract T copyModel();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showModel(T model) {
        setModel(model);
        backModel = copyModel(); //备份Model,以便后续恢复数据使用
        fillView(model);
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public T getBackModel() {
        return backModel;
    }

    public void setBackModel(T backModel) {
        this.backModel = backModel;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}
