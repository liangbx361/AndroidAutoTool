package com.nd.hy.android.auto.controller;

import com.nd.hy.android.auto.parser.HttpParseFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class MainController extends BaseController{

    @FXML
    private BorderPane rootPane;

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "postman files (*.postman_collection)", "*.postman_collection");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null) {
            List<Map<String, Object>> httpInfoList = HttpParseFactory.parsePostman(file.getAbsolutePath());
            showHttpListView(httpInfoList);
        }
    }

    public void showHttpListView(List<Map<String, Object>> httpInfoList) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/http_list_layout.fxml"));
            Pane pane = loader.load();

            rootPane.setCenter(pane);

            HttpListController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.fillTabel(httpInfoList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
