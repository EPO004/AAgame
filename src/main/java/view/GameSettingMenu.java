package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GameSetting;

import java.net.URI;
import java.net.URL;

public class GameSettingMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = GameSettingMenu.class.getResource("/fxml/gameSetting.fxml");
        Pane pane = new Pane();
        ImageView background = new ImageView(new Image(GameSettingMenu.class.getResource("/image/gameSetting.jpg").toExternalForm(), 800, 600, false, false));
        pane.getChildren().addAll(background);
        BorderPane borderPane = FXMLLoader.load(url);
        pane.getChildren().addAll(borderPane);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        primaryStage.setScene(new Scene(pane));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
