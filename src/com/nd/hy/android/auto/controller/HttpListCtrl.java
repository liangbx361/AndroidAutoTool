package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.maker.CodeProducer;
import com.nd.hy.android.auto.model.*;
import com.nd.hy.android.auto.view.CustDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class HttpListCtrl extends BaseCtrl implements Initializable{

    @FXML
    private TableView<HttpInfo> tableView;
    @FXML
    private TableColumn<HttpInfo, String> methodColumn;
    @FXML
    private TableColumn<HttpInfo, String> nameColumn;
    @FXML
    private TableColumn<HttpInfo, String> descColumn;
    @FXML
    private TableColumn<HttpInfo, Boolean> genCheckColumn;
    @FXML
    private Button baseModelBtn;

    private List<HttpInfo> httpInfoList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @Override
    protected void initView() {
        methodColumn.setCellValueFactory(cellData -> cellData.getValue().getRequest().reqMethodProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getRequest().reqNameProperty());
        descColumn.setCellValueFactory(cellData -> cellData.getValue().getRequest().reqDescriptionProperty());
        genCheckColumn.setCellValueFactory(cellData -> cellData.getValue().genFlagProperty());
        genCheckColumn.setCellFactory(CheckBoxTableCell.forTableColumn(genCheckColumn));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetail(newValue)
        );

        if(null != MainApp.project.getBaseModel()) {
            baseModelBtn.setText("修改BaseModel");
        } else {
            baseModelBtn.setText("提取BaseModel");
        }
    }

    public void fillTabelView(List<HttpInfo> httpInfoList) {
        this.httpInfoList = httpInfoList;
        ObservableList<HttpInfo> observableList = FXCollections.observableArrayList();
        observableList.addAll(httpInfoList);

        tableView.setItems(observableList);
    }

    private void showDetail(HttpInfo httpInfo) {
        System.out.println("showDetail");
    }

    /**
     * 生成代码
     */
    @FXML
    private void genCode() {
//        CodeProducer.getInstance().produceHttpCode(httpInfoList);
    }

    /**
     * 如果数据最外层是通用的，则提取最外层作为BasedModel
     */
    @FXML
    private void handlerBaseModel() {
        if(null != MainApp.project.getBaseModel() || findBaseModel()) {
            CustDialog.showCommonDialog(mainApp.getPrimaryStage(), "提取BaseModel", null,
                    CommonOptCtrl.OptType.EDIT_BASE_MODEL, MainApp.project);
        } else {
            CustDialog.showError("无法找到可提取的BaseModel");
        }
    }

    /**
     * 查换BaseModel
     * 提取最外层相同的字段作为BasedModel
     * FIXME 目前只支持整层做为BasedModel，后续可以优化为支持同级部分字段提取BaseModel
     */
    private boolean findBaseModel() {
        boolean isFind = true;
        Model firstModel = httpInfoList.get(0).getResponse().getModel();
        for(int i=1; i<httpInfoList.size(); i++) {
            Model itemModel = httpInfoList.get(i).getResponse().getModel();
            if(firstModel.getModelFieldList().size() != itemModel.getModelFieldList().size()) {
                isFind = false;
                break;
            }
            for(int j=0; j<firstModel.getModelFieldList().size(); j++) {
                if(!firstModel.getModelFieldList().get(j).equals(itemModel.getModelFieldList().get(j))) {
                    isFind = false;
                    break;
                }
            }
        }

        if(isFind) {
            Model baseModel = firstModel.copy();
            baseModel.setModelName("BaseModel");
            MainApp.project.setBaseModel(baseModel);
        }

        return isFind;

    }

    /**
     * 全选操作处理
     */
    @FXML
    private void handlerSelAll() {

    }

}
