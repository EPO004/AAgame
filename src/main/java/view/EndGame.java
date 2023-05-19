package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URL;

public class EndGame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/fxml/endGame.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        borderPane.setStyle("-fx-background-color: red");
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
