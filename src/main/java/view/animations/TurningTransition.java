package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;
import model.Connection;
import view.Game;

import java.util.Timer;

public class TurningTransition extends Transition {
    private Node node;
    double pivotX;
    double pivotY;
    double x;
    double y;
    private Connection line;
    private double primaryVelocity;
    boolean isLine;
    public double angle=90;
    Ball ball;
    double velocity;
    public TurningTransition(Node node, double pivotX, double pivotY, double velocity, double angle){
        this.node = node;
        this.velocity = velocity;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        ball = (Ball) node;
        this.angle = angle;
        primaryVelocity = velocity;
        x = 400f;
        y = 300f;
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
    }

    public TurningTransition(Node node, double pivotX, double pivotY, double velocity, boolean isLine) {
        this.node = node;
        this.pivotX = pivotX;
        line = (Connection) node;
        this.pivotY = pivotY;
        primaryVelocity = velocity;
        this.isLine = isLine;
        this.velocity = velocity;
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        if (Game.getCenterDisk().isFreezing()) velocity = primaryVelocity / 2;
        else velocity = primaryVelocity;
        angle += velocity;
        angle %=360;
        //stop();
        if (ball!=null){
            ball.setCenterX(400f + Math.cos(Math.toRadians(angle)) * 150f);
            ball.setCenterY(300f + Math.sin(Math.toRadians(angle)) * 150f);
            ball.setAngle(angle);
        }
        else {
            line.setStartX(line.getOwnerBall().getCenterX());
            line.setStartY(line.getOwnerBall().getCenterY());
        }
    }
}
