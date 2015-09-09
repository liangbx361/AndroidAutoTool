package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.parser.HttpParseFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class MainCtrl extends BaseCtrl {

    @FXML
    private BorderPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected void initView() {

    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "postman files (*.postman_collection)", "*.postman_collection");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null) {
            List<HttpInfo> httpInfoList = HttpParseFactory.parsePostman(file.getAbsolutePath());
            MainApp.project.setHttpInfoList(httpInfoList);
            showHttpList(httpInfoList);
        }
    }

    public void showHttpList(List<HttpInfo> httpInfoList) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout_http_list.fxml"));
            Pane pane = loader.load();

            rootPane.setCenter(pane);

            HttpListCtrl controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.fillTabelView(httpInfoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
