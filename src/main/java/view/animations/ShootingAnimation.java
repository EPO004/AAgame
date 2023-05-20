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
        ArrayList<Ball> balls = centerDisk.getBall();
        for (Ball b : balls){
            if (b.getBoundsInParent().intersects(ball.getBoundsInLocal())){
                centerDisk.stopTurning();
                pane.setStyle("-fx-background-color: red");
                stop();
                Game.getTimeline().stop();
                EndGame endGame = new EndGame();
                try {
                    endGame.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        if (y==centerDisk.getCenterY()+150){
            //   pane.getChildren().remove(ball);
            // this.stop();
            GameSetting gameSetting = MainMenu.getUser().getGameSetting();
            Game.getGameControl().setScoreString("Score : "+ ((gameSetting.getRealBalls()-gameSetting.getAllBalls())*10));
            pane.getChildren().remove(ball);
            ball = new Ball(centerDisk, true);
            centerDisk.addBall(ball);
            if (MainMenu.getUser().getGameSetting().getAllBalls()==0){
                pane.setStyle("-fx-background-color: green");
                centerDisk.stopTurning();
                this.stop();
                Game.getTimeline().stop();
                EndGame endGame = new EndGame();
                try {
                    endGame.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.stop();
        }
        ball.setCenterY(y);
    }
}
