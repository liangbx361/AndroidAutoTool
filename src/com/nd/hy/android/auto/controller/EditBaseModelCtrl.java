package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.ModelField;
import com.nd.hy.android.auto.util.BaseModelUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author liangbx
 * Date 2015/9/11
 */
public class EditBaseModelCtrl extends BaseController<Model> implements CommonOptCtrl.OptListener {

    @FXML
    private TextField baseModelName;
    @FXML
    private TableView<ModelField> modelFieldsTable;
    @FXML
    private TableColumn<ModelField, String> dataType;
    @FXML
    private TableColumn<ModelField, String> respFieldName;
    @FXML
    private TableColumn<ModelField, String> genFieldName;

    @Override
    protected void initView() {
        dataType.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
        respFieldName.setCellValueFactory(cellData -> cellData.getValue().respFieldNameProperty());
        genFieldName.setCellValueFactory(cellData -> cellData.getValue().genFieldNameProperty());

        modelFieldsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEditDialog(newValue)
        );
    }

    @Override
    protected void fillView(Model model) {
        baseModelName.textProperty().bindBidirectional(getModel().modelNameProperty());

        ObservableList<ModelField> observableList = FXCollections.observableArrayList();
        observableList.addAll(getModel().getModelFieldList());

        modelFieldsTable.setItems(observableList);
    }

    /**
     * 显示编辑框进行编辑
     * @param newValue
     */
    public void showEditDialog(ModelField newValue) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog_edit_model_field.fxml"));
            Parent dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("编辑字段");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getStage());
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            EditModelFieldCtrl editModelFieldCtrl = loader.getController();
            editModelFieldCtrl.setDialogStage(dialogStage);
            editModelFieldCtrl.initData(newValue);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Model copyModel() {
        return getModel().copy();
    }


    @Override
    public void handlerOk() {
        new BaseModelUtil().updateModels(getModel(), MainApp.project.getHttpInfoList());
    }

    @Override
    public void handlerCancel() {

    }
}
