package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.model.ModelField;
import com.nd.hy.android.auto.view.CustDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/7
 * DESC 编辑Model字段
 */
public class EditModelFieldCtrl extends BaseCtrl {

    @FXML
    private ChoiceBox<String> dataTypeCb;

    @FXML
    private TextField fieldNameTf;

    private Stage dialogStage;
    private ModelField baseModelField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
    }

    @Override
    protected void initView() {
        ObservableList<String> dataTypeList = FXCollections.observableArrayList();
        Field[] fields = DataType.class.getFields();
        for(int i=0; i<fields.length; i++) {
            try {
                dataTypeList.add(fields[i].get(fields[i].getName()).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        dataTypeCb.setItems(dataTypeList);
    }

    public void initData(ModelField baseModelFields) {
        this.baseModelField = baseModelFields;

        dataTypeCb.setValue(baseModelFields.getDataType());
        fieldNameTf.setText(baseModelFields.getGenFieldName());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleOk() {
        String type = dataTypeCb.getValue();
        String name = fieldNameTf.getText();

        if("".equals(name)) {
            CustDialog.showError("字段不能为空");
        }

        baseModelField.setDataType(type);
        baseModelField.setGenFieldName(name);

        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


}
