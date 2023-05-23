package view.animations;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;
import model.Connection;
import model.GameSetting;
import view.EndGame;
import view.Game;
import view.MainMenu;

import java.util.ArrayList;
import java.util.Random;

public class ShootingAnimation extends Transition {
    private Pane pane ;
    private CenterDisk centerDisk;
    private Ball ball;
    private double angle= 50;

    public ShootingAnimation(Pane pane,CenterDisk centerDisk, Ball ball) {
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(1);
    }
    public ShootingAnimation(Ball ball, CenterDisk centerDisk, Pane pane, double angle){
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.angle = angle;
        Game.getGameControl().setWindString("Wind Degree : "+angle);
        setCycleDuration(Duration.millis((5f/MainMenu.getUser().getGameSetting().getWindVelocity())*1000f));
        setCycleCount(1);
    }
    @Override
    protected void interpolate(double frac) {
        double y, x = ball.getCenterX();
        if(angle==50) y = ball.getCenterY()-10;
        else {
            y = ball.getCenterY() - 10 * Math.sin(Math.toRadians(90f + angle));
            x = ball.getCenterX() + 10* Math.cos(Math.toRadians(90f + angle));
        }
        long d = (long) Math.sqrt((x-400)*(x-400) + (y-300)*(y-300));
        if (failShoot()) return;
        if ((x > 600 || x < 200 || y < 100)) {
            outOfBorder();
            return;
        }
        if (d>148 && d<152){
            successShoot();
        }
        ball.setCenterY(y);
        ball.setCenterX(x);
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCycleDuration(Duration.millis((5f/MainMenu.getUser().getGameSetting().getWindVelocity())*1000f));
                play();
            }
        });
    }
    private void successShoot(){
        GameSetting gameSetting = MainMenu.getUser().getGameSetting();
        Game.getGameControl().setScoreString("Score : "+ ((gameSetting.getRealBalls()-gameSetting.getAllBalls())*10));
        pane.getChildren().remove(ball);
        ball = new Ball(centerDisk, true);
        applyPhase(ball);
        if (MainMenu.getUser().getGameSetting().getAllBalls()==0){
            pane.setStyle("-fx-background-color: green");
            centerDisk.stopTurning();
            this.stop();
            Game.getTimeline().stop();
            Game.endGame();
        }
        this.stop();
    }
    private boolean failShoot(){
        ArrayList<Ball> balls = centerDisk.getCenterDisk();
        for (Ball b : balls){
            if (b.getBoundsInParent().intersects(ball.getBoundsInLocal()) && ball.isVisible){
                centerDisk.stopTurning();
                pane.setStyle("-fx-background-color: red");
                MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
                this.stop();
                Game.getTimeline().stop();
                Game.endGame();
                return true;
            }
        }
        return false;
    }
    private void outOfBorder(){
        centerDisk.stopTurning();
        pane.setStyle("-fx-background-color: red");
        MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
        this.stop();
        Game.getTimeline().stop();
        Game.endGame();
    }
    private void applyPhase(Ball ball){
        int balls = MainMenu.getUser().getGameSetting().getAllBalls();
        int quarter = MainMenu.getUser().getGameSetting().getRealBalls()/4;
        if (balls >= quarter*3) centerDisk.addBall(ball);
        else if (balls >= quarter*2){
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase2RadiusChange();
        }
        else{
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase3Visible();
        }
    }
    private static int getRandom(){

        Random random = new Random();
        int number = random.nextInt(31) - 15;
        return number;
    }
}
