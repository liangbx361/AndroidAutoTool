package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.maker.CodeProducer;
import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.util.BaseModelUtil;
import com.nd.hy.android.auto.util.HttpInfoUtil;
import com.nd.hy.android.auto.view.CustDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class HttpInfoListCtrl extends BaseCtrl implements Initializable{

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
                (observable, oldValue, newValue) -> editHttpInfo(newValue)
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

    /**
     * 进入修改HttpInfo
     * @param httpInfo
     */
    private void editHttpInfo(HttpInfo httpInfo) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout_edit_http_info.fxml"));
            Parent dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("编辑Http信息");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            EditHttpInfoCtrl ctrl = loader.getController();
            ctrl.setStage(dialogStage);
            ctrl.showModel(httpInfo);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成代码
     */
    @FXML
    private void genCode() {
//        CodeProducer.getInstance().produceHttpCode(new HttpInfoUtil().beanToMap(httpInfoList));
        CodeProducer.getInstance().produceHttpCode1(httpInfoList);
    }

    /**
     * 如果数据最外层是通用的，则提取最外层作为BasedModel
     */
    @FXML
    public void handlerBaseModel() {
        if(httpInfoList.size() < 0) {
            return;
        }

        if(null != MainApp.project.getBaseModel() || null != BaseModelUtil.findBaseModel(httpInfoList)) {
            CustDialog.showCommonDialog(mainApp.getPrimaryStage(), "提取BaseModel", null,
                    CommonOptCtrl.OptType.EDIT_BASE_MODEL, MainApp.project);
        } else {
            CustDialog.showError("无法找到可提取的BaseModel");
        }
    }

    /**
     * 全选操作处理
     */
    @FXML
    private void handlerSelAll() {

    }

}
