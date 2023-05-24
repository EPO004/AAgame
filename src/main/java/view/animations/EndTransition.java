package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Ball;
import model.Connection;
import view.Game;

import java.util.ArrayList;

public class EndTransition extends Transition {
    public EndTransition(){
        setCycleDuration(Duration.seconds(20));
    }
    @Override
    protected void interpolate(double frac) {
        ArrayList<Ball> balls = Game.getCenterDisk().getCenterDisk();
        for (Ball ball : balls){
            double x = ball.getCenterX();
            double y = ball.getCenterY();
            ball.setCenterY(y+10);
            if (x>400) ball.setCenterX(x-10);
            else if (x<400) ball.setCenterX(x+10);
        }
        ArrayList<Connection> lines = Game.getCenterDisk().getLines();
        for (Connection line : lines){
            line.setStartX(line.getOwnerBall().getCenterX());
            line.setStartY(line.getOwnerBall().getCenterY());
        }
    }
}
