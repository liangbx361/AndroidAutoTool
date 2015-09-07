package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import javafx.fxml.Initializable;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public abstract class BaseCtrl implements Initializable {

    protected MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    protected abstract void initView();
}
