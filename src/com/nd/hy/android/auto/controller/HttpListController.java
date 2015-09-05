package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.maker.CodeProducer;
import com.nd.hy.android.auto.model.HttpInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.List;
import java.util.Map;

import static com.nd.hy.android.auto.parser.HttpFields.*;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class HttpListController extends BaseController {

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

            Map<String, Object> request = (Map<String, Object>) httpInfo.get(REQUEST);
            String method = (String) request.get(REQUEST_METHOD);
            String name = (String) request.get(REQUEST_NAME);
            String description = (String) request.get(REQUEST_DESCRIPTION);
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
