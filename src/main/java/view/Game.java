package view;

import controller.GameControl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.animations.FreezeAnimation;
import view.animations.WindDegree;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static WindDegree windDegree;
    private static boolean resume = false;
    private LastGame lastGame;

    public Game() {
        Game.audioClip = new AudioClip(getClass().getResource("/sound/track1.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
        Game.resume = false;
        if (windDegree!=null) windDegree.setDuration();
        windDegree = null;
    }
    public Game(boolean resume) {
        Game.resume = resume;
        windDegree = null;
        Game.audioClip = new AudioClip(getClass().getResource("/sound/track1.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
        if (windDegree!=null) windDegree.setDuration();
        windDegree = null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Game.primaryStage = primaryStage;
        URL url = getClass().getResource("/fxml/game.fxml");
        Pane pane = new Pane();
        Game.pane = pane;
        BorderPane borderPane = FXMLLoader.load(url);
        gameControl = GameControl.getGameControl();
        CenterDisk centerDisk = new CenterDisk(resume);
        System.out.println(resume);
        if (resume) {
            lastGame = gameControl.load(pane, centerDisk);
            MainMenu.getUser().getGameSetting().setAllBalls(lastGame.getRemainBalls());
            windDegree = new WindDegree();
            windDegree.play();
            System.out.println(windDegree);
        }
        pane.setStyle("-fx-background-color: wheat");
        Button pauseButton = getStyled("Pause");
        VBox vBox = new VBox(pauseButton, borderPane);
        pane.getChildren().add(vBox);
        Game.centerDisk = centerDisk;
        Circle ball = newBall(centerDisk, pane);
        Game.ball = ball;

        pauseButton.setOnMouseClicked(e -> {
            try {
                timeline.stop();
                if (windDegree!=null) {
                    windDegree.stop();
                }
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
        ball.requestFocus();
        final int[] angle = {0};
        final WindDegree[] windDegree = new WindDegree[1];
        ball.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String keyName = event.getCode().getName();
                Random random = new Random();
                boolean ballCount = MainMenu.getUser().getGameSetting().getAllBalls() <= MainMenu.getUser().getGameSetting().getRealBalls()/4 +1;
                int number = random.nextInt(31) - 15;
                if (keyName.equals(MainMenu.getUser().getGameSetting().getKeys()[0])){
                    if (ballCount && windDegree[0]==null && Game.windDegree==null) {
                        windDegree[0] = new WindDegree();
                        windDegree[0].play();
                    }
                    shoot(ball, angle, windDegree[0]);
                    Media media = new Media(getClass().getResource("/sound/shoot.mp3").toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    if (MainMenu.getUser().getGameSetting().isSoundOn()) mediaPlayer.play();
                    if (Game.windDegree==null)Game.windDegree = windDegree[0];
                }
                else if (keyName.equals(MainMenu.getUser().getGameSetting().getKeys()[2]) && MainMenu.getUser().getGameSetting().getAllBalls() <= MainMenu.getUser().getGameSetting().getRealBalls()/4 ){
                    if (ball.getCenterX()<600)ball.setCenterX(ball.getCenterX()+10);
                }
                else if (keyName.equals(MainMenu.getUser().getGameSetting().getKeys()[3]) && MainMenu.getUser().getGameSetting().getAllBalls() <= MainMenu.getUser().getGameSetting().getRealBalls()/4 ){
                    if (ball.getCenterX()>200)ball.setCenterX(ball.getCenterX()-10);
                }
                else if (keyName.equals(MainMenu.getUser().getGameSetting().getKeys()[1]) && gameControl.getFreezeProgress()==1){
                    if (!centerDisk.isFreezing()){
                        gameControl.applyFreeze();
                        centerDisk.setFreezing(true);
                        FreezeAnimation freezeAnimation = new FreezeAnimation(centerDisk);
                        freezeAnimation.play();
                        ball.requestFocus();
                        timeFreeze();
                    }
                }
            }
        });
        return ball;
    }
    private void timeFreeze(){
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(MainMenu.getUser().getGameSetting().getFreezeSecond()),
                        event -> {
                            centerDisk.setFreezing(false);
                            try {
                                stop();
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void shoot(Ball ball, int angle[], WindDegree windDegree){
        try {
            if (MainMenu.getUser().getGameSetting().getAllBalls()==0) return;
            gameControl.shoot(pane, centerDisk, ball, angle[0], windDegree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void timer(Pane pane, CenterDisk centerDisk){
        int endTime = 600;
        if (lastGame!=null){
            endTime = lastGame.getMinute()*60 + lastGame.getSecond();
        }
        AtomicInteger finalEndTime = new AtomicInteger(endTime);
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            long diff = 0;
                            if (finalEndTime.intValue()>=0) diff = finalEndTime.get() - 1;
                            finalEndTime.addAndGet(-1);
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
        if (endTime==-1) timeline.stop();
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
        if (windDegree!= null)windDegree.setDuration();
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
        if (resume) MainMenu.getUser().setLastGame(null);
    }
    private static void exit(Button button, int score, int minute, int second, Stage stage){
        second = second + minute*60;
        int finalSecond = second;
        windDegree=null;
        button.setOnMouseClicked(event -> {
            audioClip.stop();
            User user = MainMenu.getUser();
            user.setScore(user.getScore()+score);
            if (user.getPlayedTimeSecond() > finalSecond || user.getPlayedTimeSecond()==0) user.setPlayedTimeSecond(finalSecond);
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
            MainMenu mainMenu = null;
            try {
                mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                    mainMenu.start(LoginMenu.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }

    public static WindDegree getWindDegree() {
        return windDegree;
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
    public static void hint(){
        Stage hintStage = new Stage(StageStyle.TRANSPARENT);
        Label label = labelStyled("For shoot click : Space");
        Label label1 = labelStyled("For freezing click : Shift");
        Label label2 = labelStyled("For moving right click : D");
        Label label3 = labelStyled("For moving left click : A");
        Button button = getStyled("close");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(label, label1, label2, label3, button);
        hintStage.initOwner(popUpStage);
        hintStage.initModality(Modality.APPLICATION_MODAL);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        vBox.setEffect(monochrome);
        hintStage.setScene(new Scene(vBox, Color.TRANSPARENT));
        hintStage.show();
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hintStage.close();
            }
        });
    }

    public static void setWindDegree(WindDegree windDegree) {
        Game.windDegree = windDegree;
    }
}
