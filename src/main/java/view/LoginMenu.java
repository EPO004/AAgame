package view;

import controller.LoginMenuControl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class LoginMenu extends Application {
    private static Stage stage;
    @FXML
    private Label label;
    @FXML
    private TextField username;
    public static void main(String [] args) throws IOException, ParseException {
        LoginMenuControl.backUpData();
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        LoginMenu.stage = stage;
        URL url = LoginMenu.class.getResource("/fxml/loginMenu.fxml");
        ImageView backGround = new ImageView(new Image(LoginMenu.class.getResource("/image/login.png").toExternalForm(), 880, 600, false, false));
        Pane pane = new Pane();
        pane.getChildren().addAll(backGround);
        BorderPane borderPane = FXMLLoader.load(url);
        pane.getChildren().addAll(borderPane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    public void initialize(){
        username.textProperty().addListener((observable, oldText, newText) -> {
            label.setText("Welcome "+ username.getText()+"!");
        });
    }

    public static Stage getStage() {
        return stage;
    }
}
