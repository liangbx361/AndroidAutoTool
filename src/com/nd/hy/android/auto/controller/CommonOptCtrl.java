package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/9
 */
public class CommonOptCtrl implements Initializable {

    @FXML
    private BorderPane rootPane;

    public enum OptType {
        EDIT_PROJECT_CONFIG,
        EDIT_HTTP_INFO,
        EDIT_BASE_MODEL
    }

    private Stage stage;

    private OptListener listener;

    private Project project;
    private OptType optType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handlerOk() {
        if(listener != null) {
            listener.handlerOk();
        }
        stage.close();
    }

    @FXML
    private void handlerCancel() {
        if(listener != null) {
            listener.handlerCancel();
        }
        stage.close();
    }

    public interface OptListener {
        void handlerOk();
        void handlerCancel();
        void setStage(Stage stage);
    }

    public void setListener(OptListener listener) {
        this.listener = listener;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public CommonOptCtrl.OptType getOptType() {
        return optType;
    }

    public void setOptType(CommonOptCtrl.OptType optType) {
        this.optType = optType;
    }

    public void showContent() {
        switch (optType) {
            case EDIT_BASE_MODEL:
                showBaseModel();
                break;

            case EDIT_HTTP_INFO:
                break;

            case EDIT_PROJECT_CONFIG:
                break;
        }
    }

    private void showBaseModel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout_edit_base_model.fxml"));
            Node node = loader.load();

            EditBaseModelCtrl controller = loader.getController();
            controller.setStage(stage);
            controller.showModel(MainApp.project.getBaseModel());
            setListener(controller);

            rootPane.setCenter(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
