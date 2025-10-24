package com.appacademia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 👉 Troca o main_view.fxml pelo login_view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appacademia/view/login_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        // CSS global (se quiser o mesmo estilo no login)
        scene.getStylesheets().add(getClass().getResource("/com/appacademia/style.css").toExternalForm());

        stage.setTitle("AppAcademia - Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/appacademia/image/logo.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
