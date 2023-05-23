package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Ball;
import model.CenterDisk;
import model.Connection;
import model.GameSetting;
import view.*;
import view.animations.Phase2Transition;
import view.animations.ShootingAnimation;

public class GameControl {
    private static GameControl gameControl;
    @FXML
    private Label timeLeft;
    private String time = "Time Left : 10:00";
    @FXML
    private Label ballLeft;
    private String ballLeftString = "Balls Left : "+MainMenu.getUser().getGameSetting().getAllBalls();
    @FXML
    private Label score;
    private String scoreString = "Score : 0";
    @FXML
    private Label windDegree;
    private String windString = "Wind Degree : 0";

    public GameControl() {
        gameControl = this;
    }

    public void shoot(Pane pane, CenterDisk centerDisk, Ball ball, double angle) throws Exception {
        int balls = MainMenu.getUser().getGameSetting().getAllBalls();
        MainMenu.getUser().getGameSetting().setAllBalls(balls-1);
        ballLeftString = "Balls Left : "+MainMenu.getUser().getGameSetting().getAllBalls();
        initialize();
        Ball ball1 = new Ball(centerDisk);
        if (balls==1) {
            pane.getChildren().remove(ball);
        }
        ball1.setCenterX(ball.getCenterX());
        pane.getChildren().addAll(ball1);
        ShootingAnimation shootingAnimation;
        GameSetting gameSetting = MainMenu.getUser().getGameSetting();
        if (gameSetting.getAllBalls() < gameSetting.getRealBalls()/4){
            shootingAnimation = new ShootingAnimation(ball1, centerDisk, pane , angle);
            shootingAnimation.play();
        }
        else {
            shootingAnimation= new ShootingAnimation(pane,centerDisk, ball1);
            shootingAnimation.play();
        }
    }
    public void pause(MouseEvent mouseEvent) {
    }
    @FXML
    public void initialize(){
        ballLeft.setText(ballLeftString);
        timeLeft.setText(time);
        score.setText(scoreString);
        windDegree.setText(windString);
    }

    public void setTimeLeft(String time) {
        this.time = time;
        initialize();
    }

    public static GameControl getGameControl() {
        return gameControl;
    }

    public void setScoreString(String scoreString) {
        this.scoreString = scoreString;
        initialize();
    }

    public String getTime() {
        return time;
    }

    public void setWindString(String windString) {
        this.windString = windString;
        initialize();
    }
}
