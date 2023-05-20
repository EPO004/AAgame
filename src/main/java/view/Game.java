package view;

import controller.GameControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;
import model.GameSetting;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

public class Game extends Application {
    private static Pane pane;
    private static GameControl gameControl;
    private static Timeline timeline;
    private static Stage popUpStage;
    private static AudioClip audioClip;
    private static CenterDisk centerDisk;
    private static Circle ball;

    public Game() {
        Game.audioClip = new AudioClip(getClass().getResource("/sound/track1.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/fxml/game.fxml");
        Pane pane = new Pane();
        BorderPane borderPane = FXMLLoader.load(url);
        gameControl = GameControl.getGameControl();
        pane.setStyle("-fx-background-color: wheat");
        Button pauseButton = getStyled("Pause");
        VBox vBox = new VBox(pauseButton, borderPane);
        pane.getChildren().add(vBox);
        CenterDisk centerDisk = new CenterDisk();
        Game.centerDisk = centerDisk;
        Circle ball = newBall(centerDisk, pane);
        Game.ball = ball;
        Game.pane = pane;

        pauseButton.setOnMouseClicked(e -> {
            try {
                timeline.stop();
                centerDisk.stopTurning();
                pauseMenu(primaryStage);
                for (int i =5; i<centerDisk.getBall().size(); i++){
                    Ball ball1 = (Ball) centerDisk.getBall().get(i);
                    System.out.println(ball1.toString());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pane.getChildren().addAll(centerDisk.getCenterDisk());
        pane.getChildren().addAll(ball);
        pane.getChildren().addAll(centerDisk.getLines());
        timer(pane, centerDisk);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        primaryStage.setScene(new Scene(pane));
        ball.requestFocus();
        if (MainMenu.getUser().getGameSetting().isMusicOn()) audioClip.play();
        primaryStage.show();
    }
    private Ball newBall(CenterDisk centerDisk, Pane pane){
        Ball ball = new Ball(centerDisk);
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String keyName = event.getCode().getName();
                if (keyName.equals("Space")){
                    try {
                        if (MainMenu.getUser().getGameSetting().getAllBalls()==0) return;
                        gameControl.shoot(pane, centerDisk, ball);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //centerDisk.addBall(new Ball(centerDisk, true));
                }
            }
        });
        return ball;
    }
    private void timer(Pane pane, CenterDisk centerDisk){
        AtomicLong endTime = new AtomicLong(600);
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            long diff = 0;
                            if (endTime.intValue()>=0) diff = endTime.get() - 1;
                            endTime.addAndGet(-1);
                            if ( diff == -1 ) {
                                centerDisk.stopTurning();
                                pane.setStyle("-fx-background-color: red");
                                EndGame endGame = new EndGame();
                                try {
                                    stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    endGame.start(new Stage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (diff>=0){
                                long temp = diff/60;
                                GameControl.getGameControl().setTimeLeft(String.format("Time Left : %02d:%02d", temp, diff-temp*60));
                                //System.out.println(gameControl);
                            }
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        if (endTime.intValue()==-1) timeline.stop();
        else timeline.play();
    }

    public static Pane getPane() {
        return pane;
    }

    public static Timeline getTimeline() {
        return timeline;
    }

    public static GameControl getGameControl() {
        return gameControl;
    }
    private void pauseMenu(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/fxml/pause.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(primaryStage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(borderPane, Color.TRANSPARENT));
        popUpStage = popupStage;
        popupStage.show();
    }
    private Button getStyled(String name){
        Button button = new Button(name);
        button.getStylesheets().add(getClass().getResource("/css/game.css").toExternalForm());
        button.getStyleClass().add("ButtonSign");
        return button;
    }

    public static Stage getPopUpStage() {
        return popUpStage;
    }

    public static CenterDisk getCenterDisk() {
        return centerDisk;
    }

    public static AudioClip getAudioClip() {
        return audioClip;
    }

    public static Circle getBall() {
        return ball;
    }

    public static void setAudioClip(AudioClip audioClip) {
        Game.audioClip = audioClip;
    }
}
