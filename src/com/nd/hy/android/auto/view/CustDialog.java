package com.nd.hy.android.auto.view;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.controller.CommonOptCtrl;
import com.nd.hy.android.auto.model.Project;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.dialog.CommandLinksDialog;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author liangbx
 * Date 2015/9/7
 */
public class CustDialog {

    public static void showError(String message) {
        System.out.println(message);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(message);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showMessage(Stage owner, String message) {
        System.out.println(message);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("信息");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * 显示异常信息
     */
    public static void showException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, an Exception Dialog");
        alert.setContentText("Could not find file blabla.txt!");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static CommonOptCtrl showCommonDialog(Stage primaryStage, String title, String iconRes, CommonOptCtrl.OptType optType, Project project) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog_common_opt.fxml"));
            Parent dialog = loader.load();

            Stage dialogStage = new Stage();
            if (title != null && !"".equals(title)) {
                dialogStage.setTitle(title);
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            if (iconRes != null && !"".equals(iconRes)) {
                dialogStage.getIcons().add(new Image(iconRes));
            }
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            final CommonOptCtrl ctrl = loader.getController();
            ctrl.setStage(dialogStage);
            ctrl.setProject(project);
            ctrl.setOptType(optType);
            ctrl.showContent();

            dialogStage.showAndWait();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
