package view;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;

import java.util.ArrayList;

public class ShootingAnimation extends Transition {
    private Pane pane ;
    private CenterDisk centerDisk;
    private Ball ball;

    public ShootingAnimation(Pane pane,CenterDisk centerDisk, Ball ball) {
        this.pane = pane;
        this.centerDisk = centerDisk;
        this.ball = ball;
        this.setCycleDuration(Duration.seconds(1));
        this.setCycleCount(-1);
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
                return;
            }
        }
        if (y==centerDisk.getCenterY()+150){
         //   pane.getChildren().remove(ball);
           // this.stop();
            pane.getChildren().remove(ball);
            ball = new Ball(centerDisk, true);
            centerDisk.addBall(ball);
            if (MainMenu.getUser().getGameSetting().getAllBalls()==0){
                pane.setStyle("-fx-background-color: green");
                //pane.getChildren().remove(ball);
                centerDisk.stopTurning();
            }
            // pane.getChildren().remove(centerDisk);
            this.stop();
           // System.out.println(1);
        }
        ball.setCenterY(y);
    }
}
