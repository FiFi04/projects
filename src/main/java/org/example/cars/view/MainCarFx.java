package org.example.cars.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.cars.controller.MainPaneController;

public class MainCarFx extends Application {
    MainPaneController mainPaneController;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/carsApp/javaFx/mainPane.fxml"));
        BorderPane borderPane = loader.load(getClass().getResourceAsStream("/carsApp/javaFx/mainPane.fxml"));
        mainPaneController = loader.getController();
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("CarApp");
        stage.show();
        stage.setUserData(borderPane);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        mainPaneController.interruptWriteThread();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
