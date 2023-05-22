package view.animations;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
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

public class ShootingAnimation extends Transition {
    private Pane pane ;
    private CenterDisk centerDisk;
    private Ball ball;

    public ShootingAnimation(Pane pane,CenterDisk centerDisk, Ball ball) {
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(1);
    }

    @Override
    protected void interpolate(double frac) {
        double y = ball.getCenterY()-10;
        ArrayList<Ball> balls = centerDisk.getCenterDisk();
        for (Ball b : balls){
            if (b.getBoundsInParent().intersects(ball.getBoundsInLocal()) && ball.isVisible){
                centerDisk.stopTurning();
                pane.setStyle("-fx-background-color: red");
                MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
                this.stop();
                Game.getTimeline().stop();
                Game.endGame();
                return;
            }
        }
        if (y==centerDisk.getCenterY()+150){
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
        ball.setCenterY(y);
    }
    private void applyPhase(Ball ball){
        int balls = MainMenu.getUser().getGameSetting().getAllBalls();
        int quarter = MainMenu.getUser().getGameSetting().getRealBalls()/4;
        if (balls >= quarter*4) centerDisk.addBall(ball);
        else if (balls >= quarter*3){
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase2RadiusChange();
        }
        else if (balls>= quarter*2){
            centerDisk.addBall(ball);
            centerDisk.stopTurning();
            centerDisk.makeShootedPhase2();
            centerDisk.phase3Visible();
        }
    }
}
