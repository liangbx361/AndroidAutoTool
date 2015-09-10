package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.model.Request;
import com.nd.hy.android.auto.model.RequestParam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * Author liangbx
 * Date 2015/9/10
 */
public class EditHttpInfoCtrl<HttpInfo> extends BaseController<HttpInfo> implements Initializable {

    @FXML
    private TextField requestNameTf;
    @FXML
    private TextField requestMethodTf;
    @FXML
    private TextField requestPathTf;
    @FXML
    private TableView<RequestParam> paramsTv;
    @FXML
    private TableColumn<RequestParam, String> pUseTypeTc;
    @FXML
    private TableColumn<RequestParam, String> pUrlNameTc;
    @FXML
    private TableColumn<RequestParam, String> pDataTypeTc;
    @FXML
    private TableColumn<RequestParam, String> pValueNameTc;

    @Override
    protected void initView() {
        pUseTypeTc.setCellValueFactory(cellData -> cellData.getValue().typeForUrlProperty());
        pUrlNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForUrlProperty());
        pDataTypeTc.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
        pValueNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForUrlProperty());
    }

    @Override
    protected void fillView(HttpInfo model) {
        com.nd.hy.android.auto.model.HttpInfo httpInfo = (com.nd.hy.android.auto.model.HttpInfo)model;
        Request request = httpInfo.getRequest();
        requestNameTf.textProperty().bindBidirectional(request.reqNameProperty());
        requestMethodTf.textProperty().bindBidirectional(request.reqMethodProperty());
        requestPathTf.textProperty().bindBidirectional(request.reqPathProperty());

        ObservableList<RequestParam> observableList = FXCollections.observableArrayList();
        observableList.addAll(request.getRequestParamList());
        paramsTv.setItems(observableList);
    }

    @Override
    protected HttpInfo copyModel() {
        return null;
    }

    @FXML
    public void handlerOk() {
        getStage().close();
    }

    @FXML
    public void handlerCancel() {
        getStage().close();
    }
}
