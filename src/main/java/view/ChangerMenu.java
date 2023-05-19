package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class ChangerMenu extends Application {
    private boolean state;

    public ChangerMenu(boolean state) {
        this.state = state;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url;
        if (state) url = LoginMenu.class.getResource("/fxml/userChanger.fxml");
        else url = LoginMenu.class.getResource("/fxml/passwordChanger.fxml");
        ImageView backGround = new ImageView(new Image(LoginMenu.class.getResource("/image/login.png").toExternalForm(), 880, 600, false, false));
        Pane pane = new Pane();
        pane.getChildren().addAll(backGround);
        BorderPane borderPane = FXMLLoader.load(url);
        pane.getChildren().addAll(borderPane);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
