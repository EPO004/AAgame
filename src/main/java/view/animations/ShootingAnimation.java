package view.animations;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    private double angle = 50;
    private WindDegree windDegree;

    public ShootingAnimation(Pane pane,CenterDisk centerDisk, Ball ball) {
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.windDegree = Game.getWindDegree();
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(1);
    }
    public ShootingAnimation(Ball ball, CenterDisk centerDisk, Pane pane, double angle, WindDegree windDegree){
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.angle = angle;
        this.windDegree = Game.getWindDegree();
        Game.getGameControl().setWindString("Wind Degree : "+angle);
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
    }
    @Override
    protected void interpolate(double frac) {
        if (windDegree!=null)
            angle = windDegree.angle;
        double y, x = ball.getCenterX();
        if(angle==50) y = ball.getCenterY()-15;
        else {
            y = ball.getCenterY() - 15 * Math.sin(Math.toRadians(90f + angle));
            x = ball.getCenterX() + 15* Math.cos(Math.toRadians(90f + angle));
        }
        double d =  Math.sqrt((x-400)*(x-400) + (y-300)*(y-300));
        if (failShoot()) return;
        if ((x > 750 || x < 50 || y < 50)) {
            outOfBorder();
            return;
        }
        if (d>=148 && d<=152){
            successShoot();
        }
        ball.setCenterY(y);
        ball.setCenterX(x);
    }
    private void successShoot(){
        GameSetting gameSetting = MainMenu.getUser().getGameSetting();
        Game.getGameControl().setScoreString("Score : "+ ((gameSetting.getRealBalls()-gameSetting.getAllBalls())*10));
        pane.getChildren().remove(ball);
        Game.getGameControl().updateFreeze();
        ball = new Ball(centerDisk, true);
        applyPhase(ball);
        if (MainMenu.getUser().getGameSetting().getAllBalls()==0){
            pane.setStyle("-fx-background-color: green");
            if (Game.getWindDegree()!=null) Game.getWindDegree().stop();
            centerDisk.stopTurning();
            this.stop();
            Game.getPane().getChildren().remove(Game.getBall());
            Game.getPane().getChildren().remove(ball);
            EndTransition endTransition = new EndTransition();
            endTransition.play();
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
                System.out.println(Game.getWindDegree());
                if (Game.getWindDegree()!=null) {
                    Game.getWindDegree().stop();
                }
                MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
                this.stop();
                Game.getPane().getChildren().remove(Game.getBall());
                Game.getPane().getChildren().remove(ball);
                EndTransition endTransition = new EndTransition();
                endTransition.play();
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
        if (Game.getWindDegree()!=null) Game.getWindDegree().stop();
        this.stop();
        Game.getPane().getChildren().remove(Game.getBall());
        Game.getPane().getChildren().remove(ball);
        EndTransition endTransition = new EndTransition();
        endTransition.play();
        Game.getTimeline().stop();
        Game.endGame();
    }
    private void applyPhase(Ball ball){
        int balls = MainMenu.getUser().getGameSetting().getAllBalls();
        int quarter = MainMenu.getUser().getGameSetting().getRealBalls()/4;
        if (balls > quarter*3) centerDisk.addBall(ball);
        else if (balls > quarter*2){
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase2RadiusChange();
        }
        else{
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase2RadiusChange();
            centerDisk.phase3Visible();
        }
    }
    private static int getRandom(){

        Random random = new Random();
        int number = random.nextInt(31) - 15;
        return number;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
