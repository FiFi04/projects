package org.example.cars.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.cars.service.CarService;

public class MainCarFx extends Application {

    public static boolean isProgramRunning = true;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("/carsApp/javaFx/mainPane.fxml"));
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("CarApp");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        isProgramRunning = false;
        CarService.closeWriteThread();
        super.stop();
    }
}
