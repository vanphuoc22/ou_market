package com.tdkhoa.oumarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private Stage Stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.Stage = stage;
//        scene = new Scene(loadFXML("login"), 600, 400);
//        stage.setScene(scene);
//        stage.setTitle("Đăng Nhập");
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}