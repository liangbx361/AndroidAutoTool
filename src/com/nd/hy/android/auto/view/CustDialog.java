package com.nd.hy.android.auto.view;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.controller.CommonOptCtrl;
import com.nd.hy.android.auto.model.Project;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Author liangbx
 * Date 2015/9/7
 */
public class CustDialog {

    public static void showError(String message) {
        System.out.println(message);
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
