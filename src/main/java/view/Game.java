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
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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
    private static Stage primaryStage;

    public Game() {
        Game.audioClip = new AudioClip(getClass().getResource("/sound/track1.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game.primaryStage = primaryStage;
        URL url = getClass().getResource("/fxml/game.fxml");
        Pane pane = new Pane();
        Game.pane = pane;
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

        pauseButton.setOnMouseClicked(e -> {
            try {
                timeline.stop();
                centerDisk.stopTurning();
                pauseMenu(primaryStage);
                for (int i =5; i<centerDisk.getCenterDisk().size(); i++){
                    Ball ball1 = (Ball) centerDisk.getCenterDisk().get(i);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pane.getChildren().add(centerDisk);
        pane.getChildren().addAll(ball);
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
        ball.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
                                try {
                                    stop();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Game.endGame();
                            } else if (diff>=0){
                                long temp = diff/60;
                                GameControl.getGameControl().setTimeLeft(String.format("Time Left : %02d:%02d", temp, diff-temp*60));
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
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        borderPane.setEffect(monochrome);
        popupStage.setScene(new Scene(borderPane, Color.TRANSPARENT));
        popUpStage = popupStage;
        popupStage.show();
    }
    private static Button getStyled(String name){
        Button button = new Button(name);
        button.getStylesheets().add(Game.class.getResource("/css/game.css").toExternalForm());
        button.getStyleClass().add("ButtonSign");
        return button;
    }
    private static Label labelStyled(String name){
        Label button = new Label(name);
        button.getStylesheets().add(Game.class.getResource("/css/game.css").toExternalForm());
        button.getStyleClass().add("Label");
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
    public static void endGame(){
        GameSetting gameSetting = MainMenu.getUser().getGameSetting();
        int finalScore = (gameSetting.getRealBalls()-gameSetting.getAllBalls())*10;
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        Label state;
        if (gameSetting.getAllBalls()==0) state = labelStyled("Winner");
        else state = labelStyled("Loser");
        Label score = labelStyled("Your score is "+(gameSetting.getRealBalls()-gameSetting.getAllBalls())*10);
        int minute = Integer.parseInt(gameControl.getTime().substring(12, 14));
        minute = 9-minute;
        if (minute<0) minute = 0;
        if (minute ==9) minute = 10;
        int second = Integer.parseInt(gameControl.getTime().substring(15, 17));
        second = (60-second)%60;
        Label passedTime = labelStyled(String.format("Passed Time : %02d:%02d", minute, second));
        Button button = new Button("Exit");
        button.getStylesheets().add(Game.class.getResource("/css/game.css").toExternalForm());
        button.getStyleClass().add("Button");
        borderPane.setCenter(vBox);
        borderPane.resize(200, 300);
        vBox.getChildren().addAll(state, score, passedTime, button);
        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(primaryStage);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        popupStage.initModality(Modality.APPLICATION_MODAL);
        borderPane.setEffect(monochrome);
        popupStage.setScene(new Scene(borderPane, Color.TRANSPARENT));
        popUpStage = popupStage;
        exit(button, finalScore, minute, second, popupStage);
        popupStage.show();
    }
    private static void exit(Button button, int score, int minute, int second, Stage stage){
        second = second + minute*60;
        int finalSecond = second;
        button.setOnMouseClicked(event -> {
            audioClip.stop();
            User user = MainMenu.getUser();
            user.setScore(user.getScore()+score);
            if (user.getPlayedTimeSecond() > finalSecond) user.setPlayedTimeSecond(finalSecond);
            if (user.getGameSetting().getAllBalls() == 0){
                if (user.getGameSetting().getDifficulty()==1)
                    user.setEasyPlayed(user.getEasyPlayed()+1);
                else if (user.getGameSetting().getDifficulty()==2)
                    user.setNormalPlayed(user.getNormalPlayed()+1);
                else
                    user.setHardPlayed(user.getHardPlayed()+1);
            }
                stage.close();
                user.getGameSetting().setAllBalls(user.getGameSetting().getRealBalls());
                try {
                    saveToJson(user);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
                try {
                    mainMenu.start(LoginMenu.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }
    private static void saveToJson(User user) throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(user.getUsername())){
                temp.put("score", user.getScore());
                temp.put("playedTime", user.getPlayedTimeSecond());
                temp.put("hard", user.getHardPlayed());
                temp.put("normal", user.getNormalPlayed());
                temp.put("easy", user.getEasyPlayed());
            }
            ((JSONObject) j).put("user", temp);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
