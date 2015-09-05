package com.nd.hy.android.auto;

import com.nd.hy.android.auto.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private Parent rootParent;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Android Auto Tools");

        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/main_layout.fxml"));
            rootParent = loader.load();

            MainController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootParent);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
