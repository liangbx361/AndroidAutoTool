package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.model.ModelField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/7
 */
public class BaseModelCtrl extends BaseCtrl implements Initializable {

    @FXML
    private TextField baseModelName;
    @FXML
    private TableView<ModelField> modelFieldsTable;
    @FXML
    private TableColumn<ModelField, String> dataType;
    @FXML
    private TableColumn<ModelField, String> respFieldsName;
    @FXML
    private TableColumn<ModelField, String> genFieldsName;

    private List<ModelField> baseModelFieldsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    protected void initView() {
        dataType.setCellValueFactory(cellData -> cellData.getValue().dataTypeProperty());
        respFieldsName.setCellValueFactory(cellData -> cellData.getValue().respFieldsNameProperty());
        genFieldsName.setCellValueFactory(cellData -> cellData.getValue().genFieldsNameProperty());

        modelFieldsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEditDialog(newValue)
        );
    }

    public void fillTable(List<ModelField> baseModelFieldsList) {
        this.baseModelFieldsList = baseModelFieldsList;

        ObservableList<ModelField> observableList = FXCollections.observableArrayList();
        observableList.addAll(baseModelFieldsList);

        modelFieldsTable.setItems(observableList);
    }

    public void showEditDialog(ModelField newValue) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog_edit_model_field.fxml"));
            Parent dialog = loader.load();


            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("编辑字段");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            EditModelFieldCtrl editModelFieldCtrl = loader.getController();
            editModelFieldCtrl.setDialogStage(dialogStage);
            editModelFieldCtrl.initData(newValue);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
