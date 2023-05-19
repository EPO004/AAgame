package view;

import controller.GameControl;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;
import view.animations.TurningTransition;

import java.net.URL;

public class Game extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/fxml/game.fxml");
        Pane pane = FXMLLoader.load(url);
        pane.setStyle("-fx-background-color: wheat");
        CenterDisk centerDisk = new CenterDisk();
        Circle ball = newBall(centerDisk, pane);


        pane.getChildren().addAll(centerDisk.getCenterDisk());
        pane.getChildren().addAll(ball);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        primaryStage.setScene(new Scene(pane));
        pane.getChildren().get(1).requestFocus();
        primaryStage.show();
    }
    private Ball newBall(CenterDisk centerDisk, Pane pane){
        Ball ball = new Ball(centerDisk);
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String keyName = event.getCode().getName();
                if (keyName.equals("Space")){
                    GameControl.shoot(pane, centerDisk, ball);
                    //centerDisk.addBall(new Ball(centerDisk, true));
                }
            }
        });
        return ball;
    }
}
