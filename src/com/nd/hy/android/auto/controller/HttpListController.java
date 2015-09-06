package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.maker.CodeProducer;
import com.nd.hy.android.auto.model.HttpInfo;
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
public class HttpListController extends BaseController implements Initializable{

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

    private List<Map<String, Object>> httpInfoList;
    private ObservableList<HttpInfo> httpInfoData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void initialize() {
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

        tableView.setItems(httpInfoData);
    }

    private void fillData() {
        httpInfoData = FXCollections.observableArrayList();
        for(int i=0; i<httpInfoList.size(); i++) {
            Map<String, Object> httpInfo = httpInfoList.get(i);

            Map<String, Object> request = (Map<String, Object>) httpInfo.get(HttpFields.REQUEST);
            String method = (String) request.get(HttpFields.REQUEST_METHOD);
            String name = (String) request.get(HttpFields.REQUEST_NAME);
            String description = (String) request.get(HttpFields.REQUEST_DESCRIPTION);
            httpInfoData.add(new HttpInfo(method, name, description));
        }
    }

    private void showDetail(HttpInfo httpInfo) {
        System.out.println("showDetail");
    }

    @FXML
    private void genCode() {
        CodeProducer.getInstance().produceHttpCode(httpInfoList);
    }

}
