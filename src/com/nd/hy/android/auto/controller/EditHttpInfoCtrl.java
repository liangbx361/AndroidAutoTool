package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.define.BaseDataType;
import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.define.RetrofitParams;
import com.nd.hy.android.auto.model.*;
import com.nd.hy.android.auto.util.DefineUtil;
import com.nd.hy.android.auto.util.StringHelper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;

import javax.xml.crypto.Data;


/**
 * Author liangbx
 * Date 2015/9/10
 */
public class EditHttpInfoCtrl extends BaseController<HttpInfo> implements Initializable {

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
    @FXML
    private TreeTableView<ModelField> modelTtv;
    @FXML
    private TreeTableColumn<ModelField, String> mDataTypeTtc;
    @FXML
    private TreeTableColumn<ModelField, String> mRespFieldNameTtc;
    @FXML
    private TreeTableColumn<ModelField, String> mGenFieldNameTtc;

    private com.nd.hy.android.auto.model.HttpInfo httpInfo;

    @Override
    protected void initView() {
        //请求参数 数据
        pUseTypeTc.setCellValueFactory(cellData -> cellData.getValue().typeForUrlProperty());
        pUseTypeTc.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableList(
                DefineUtil.getDefineList(RetrofitParams.class))));
        pUseTypeTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setTypeForUrl(t.getNewValue());
                });

        pUrlNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForUrlProperty());

        pDataTypeTc.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
        pDataTypeTc.setCellFactory(ChoiceBoxTableCell.forTableColumn(FXCollections.observableList(
                DefineUtil.getDefineList(BaseDataType.class))));
        pDataTypeTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setDataType(t.getNewValue());
                }
        );

        pValueNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForFnProperty());
        pValueNameTc.setCellFactory(TextFieldTableCell.forTableColumn());
        pValueNameTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setNameForFn(t.getNewValue());
                }
        );

        //Model 数据
        mDataTypeTtc.setCellValueFactory(cellData -> cellData.getValue().getValue().dataTypeProperty());
        mDataTypeTtc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        mDataTypeTtc.setOnEditCommit(
                t -> {
                    //FIXME 需要判断哪些属于数据类型更改，哪些属于类名修改
                    int row = t.getTreeTablePosition().getRow();

                    if(row == 0) {
                        //修改类名
                        httpInfo.getResponse().getModel().setModelName(t.getNewValue());
                    } else {
                        ModelField modelField = t.getTreeTableView().getTreeItem(row).getValue();
                        modelField.setDataType(t.getNewValue());
                        if(null != modelField.getSubModel()) {
                            modelField.getSubModel().setModelName(StringHelper.getClassName(t.getNewValue()));
                        }
                    }

                }
        );

        mRespFieldNameTtc.setCellValueFactory(cellData -> cellData.getValue().getValue().respFieldNameProperty());

        mGenFieldNameTtc.setCellValueFactory(cellData -> cellData.getValue().getValue().genFieldNameProperty());
        mGenFieldNameTtc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        mGenFieldNameTtc.setOnEditCommit(
                t -> {
                    t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().
                            setGenFieldName(t.getNewValue());
                }
        );
    }

    @Override
    protected void fillView(HttpInfo model) {
        this.httpInfo = model;
        Request request = httpInfo.getRequest();
        requestNameTf.textProperty().bindBidirectional(request.reqNameProperty());
        requestMethodTf.textProperty().bindBidirectional(request.reqMethodProperty());
        requestPathTf.textProperty().bindBidirectional(request.reqPathProperty());

        ObservableList<RequestParam> observableList = FXCollections.observableArrayList();
        observableList.addAll(request.getRequestParamList());
        paramsTv.setItems(observableList);
        paramsTv.setEditable(true);

        fillTreeView();
    }

    private void genTreeItem(TreeItem root, Model model) {
        for(ModelField modelField : model.getModelFieldList()) {
            TreeItem<ModelField> treeItem = new TreeItem<>(modelField);
            root.getChildren().add(treeItem);

            if(null != modelField.getSubModel()) {
                treeItem.setExpanded(false);
                genTreeItem(treeItem, modelField.getSubModel());
            }
        }
    }

    private void fillTreeView() {
        Response response = httpInfo.getResponse();
        Model model = response.getModel();
        TreeItem<ModelField> rootItem = new TreeItem<>(new ModelField(model.getModelName(), "", ""));
        rootItem.setExpanded(true);
        genTreeItem(rootItem, model);

        modelTtv.setRoot(rootItem);
        modelTtv.setEditable(true);
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
