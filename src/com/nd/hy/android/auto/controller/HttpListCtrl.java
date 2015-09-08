package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.maker.CodeProducer;
import com.nd.hy.android.auto.model.HttpShowInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class HttpListCtrl extends BaseCtrl implements Initializable{

    @FXML
    private TableView<HttpShowInfo> tableView;
    @FXML
    private TableColumn<HttpShowInfo, String> methodColumn;
    @FXML
    private TableColumn<HttpShowInfo, String> nameColumn;
    @FXML
    private TableColumn<HttpShowInfo, String> descColumn;
    @FXML
    private TableColumn<HttpShowInfo, Boolean> genCheckColumn;

    private List<Map<String, Object>> httpInfoList;
    private ObservableList<HttpShowInfo> httpShowInfoData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @Override
    protected void initView() {
        methodColumn.setCellValueFactory(cellData -> cellData.getValue().methodProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        genCheckColumn.setCellValueFactory(cellData -> cellData.getValue().isGenProperty());
        genCheckColumn.setCellFactory(CheckBoxTableCell.forTableColumn(genCheckColumn));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetail(newValue)
        );
    }

    public void fillTabel(List<Map<String, Object>> httpInfoList) {
        this.httpInfoList = httpInfoList;
        fillData();

        tableView.setItems(httpShowInfoData);
    }

    private void fillData() {
        httpShowInfoData = FXCollections.observableArrayList();
        for(int i=0; i<httpInfoList.size(); i++) {
            Map<String, Object> httpInfo = httpInfoList.get(i);

            Map<String, Object> request = (Map<String, Object>) httpInfo.get(HttpFields.REQUEST);
            String method = (String) request.get(HttpFields.REQUEST_METHOD);
            String name = (String) request.get(HttpFields.REQUEST_NAME);
            String description = (String) request.get(HttpFields.REQUEST_DESCRIPTION);
            httpShowInfoData.add(new HttpShowInfo(method, name, description));
        }
    }

    private void showDetail(HttpShowInfo httpShowInfo) {
        System.out.println("showDetail");
    }

    @FXML
    private void genCode() {
        CodeProducer.getInstance().produceHttpCode(httpInfoList);
    }

    /**
     * 如果数据最外层是通用的，则提取最外层作为BasedModel
     */
    @FXML
    private void handlerBaseModel() {

    }

    private void getBaseModel() {

    }
}
