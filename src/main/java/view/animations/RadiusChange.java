package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;
import view.Game;
import view.MainMenu;

import java.util.ArrayList;

public class RadiusChange extends Transition {
    private Ball ball;
    private static double realRadius;

    public RadiusChange(Ball ball) {
        this.ball = ball;
        realRadius = ball.getRadius();
        setCycleDuration(Duration.seconds(1));
    }

    @Override
    protected void interpolate(double frac) {
        double percent = 5f/276f;
        CenterDisk centerDisk = Game.getCenterDisk();
        ball.setRadius(ball.getRadius()+percent);
        ArrayList<Ball> balls = centerDisk.getCenterDisk();
        for (int i =0; i<balls.size(); i++){
            for (int j=i+1; j<balls.size(); j++){
                Ball a = balls.get(i);
                Ball b = balls.get(j);
                double d = Math.sqrt((a.getCenterX()-b.getCenterX())*(a.getCenterX()-b.getCenterX()) + (a.getCenterY()-b.getCenterY())*(a.getCenterY()-b.getCenterY()));
                if (d <= a.getRadius()+b.getRadius()){
                    centerDisk.stopTurning();
                    Game.getPane().setStyle("-fx-background-color: red");
                    MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
                    Game.getTimeline().stop();
                    balls.clear();
                    Game.endGame();
                    return;
                }
            }
        }
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCycleDuration(Duration.seconds(1));
                ball.setRadius(realRadius);
                play();
            }
        });
    }
}
