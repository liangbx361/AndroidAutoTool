package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.define.RetrofitParams;
import com.nd.hy.android.auto.model.*;
import com.nd.hy.android.auto.util.DefineUtil;
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
        pUseTypeTc.setCellValueFactory(cellData -> cellData.getValue().typeForUrlProperty());
        ObservableList<String> observableList = FXCollections.observableList(DefineUtil.getDefineList(RetrofitParams.class));
        pUseTypeTc.setCellFactory(ChoiceBoxTableCell.forTableColumn(observableList));
        pUseTypeTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setTypeForUrl(t.getNewValue());
                });

        pUrlNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForUrlProperty());

        pDataTypeTc.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
        pDataTypeTc.setCellFactory(TextFieldTableCell.forTableColumn());
        pDataTypeTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setDataType(t.getNewValue());
                }
        );

        pValueNameTc.setCellValueFactory(cellData -> cellData.getValue().nameForUrlProperty());
        pValueNameTc.setCellFactory(TextFieldTableCell.forTableColumn());
        pValueNameTc.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setNameForFn(t.getNewValue());
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

        mDataTypeTtc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ModelField, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ModelField, String> param) {
                return param.getValue().getValue().dataTypeProperty();
            }
        });

//        mDataTypeTtc.setCellValueFactory(cellData -> cellData.getValue().getValue().dataTypeProperty());
//        mDataTypeTtc.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModelField, String> p) ->
//            new ReadOnlyStringWrapper(p.getValue().getValue().getDataType()));
        mDataTypeTtc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        mRespFieldNameTtc.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModelField, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getRespFieldName()));
        mRespFieldNameTtc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        mGenFieldNameTtc.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModelField, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getGenFieldName()));
        mGenFieldNameTtc.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

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
