package com.nd.hy.android.auto;

import com.nd.hy.android.auto.controller.BaseModelCtrl;
import com.nd.hy.android.auto.controller.MainCtrl;
import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.model.ModelField;
import com.nd.hy.android.auto.model.Project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/2
 */
public class MainApp extends Application {

    public final static Project project = new Project();

    private Stage primaryStage;
    private Parent rootParent;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Android Http代码自动生成工具");
        initProject();
        initRootLayout();

//        testTable(0, primaryStage);
    }

    private void initProject() {
        //FIXME 需要读取工程的信息，进行初始化。目前进行写死
        project.setPackageName("com.nd.hy.android.auto");
        project.setGenPath("/gen");
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/layout_main.fxml"));
            rootParent = loader.load();

            MainCtrl controller = loader.getController();
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

//    public void testTable(int index, Stage primaryStage) {
//
//        switch (index) {
//            case 0:
//                showBaseModel();
//                break;
//
//        }
//    }
//
//    private void showBaseModel() {
//        List<ModelField> list = new ArrayList<>();
//        list.add(new ModelField("int", "code", "code"));
//        list.add(new ModelField("String", "message", "message"));
//        list.add(new ModelField("T", "data", "data"));
//
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("view/layout_edit_base_model.fxml"));
//            Parent rootPane = loader.load();
//
//            BaseModelCtrl controller = loader.getController();
//            controller.setMainApp(this);
//            controller.fillTable(list);
//
//            Scene scene = new Scene(rootPane);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Field[] fields = DataType.class.getFields();
//        for(int i=0; i<fields.length; i++) {
//
//        }
//
//    }
}
