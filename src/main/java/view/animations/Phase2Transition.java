package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;
import model.Connection;
import view.Game;

import java.util.Random;
import java.util.Timer;

public class Phase2Transition extends Transition {
    private Node node;
    private static Phase2Transition firstOne;
    double pivotX;
    double pivotY;
    double x;
    private Connection line;
    public static int number;
    double y;
    private boolean transitionPlaying;
    private int previousTime;
    private static int nextTime = getRandom();
    boolean isLine;
    public double angle = 90;
    Ball ball;
    double velocity;
    double primaryVelocity;

    public Phase2Transition(Node node, double pivotX, double pivotY, double velocity, double angle) {
        this.node = node;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.velocity = velocity;
        ball = (Ball) node;
        primaryVelocity = velocity;
        this.angle = angle;
        x = ball.getCenterX();
        y = ball.getCenterY();
        setCycleDuration(Duration.seconds(nextTime));
        setCycleCount(1);
        if (firstOne==null) firstOne = this;
    }
    public Phase2Transition(Node node, double pivotX, double pivotY, double velocity, boolean isLine) {
        this.node = node;
        primaryVelocity = velocity;
        this.pivotX = pivotX;
        line = (Connection) node;
        this.pivotY = pivotY;
        this.velocity = velocity;
        setCycleDuration(Duration.seconds(nextTime));
        setCycleCount(1);
    }

    private static int getRandom(){

        Random random = new Random();
        int number = random.nextInt(6) + 4;
        return number;
    }

    @Override
    protected void interpolate(double frac) {
        if (Game.getCenterDisk().isFreezing()) velocity = primaryVelocity/2;
        else velocity = primaryVelocity;
      //  System.out.println(nextTime);
        angle += velocity;
        angle%=360;
        if (ball!=null) {
           // System.out.println(1);
            ball.setCenterX(400f + Math.cos(Math.toRadians(angle)) * 150f);
            ball.setCenterY(300f + Math.sin(Math.toRadians(angle)) * 150f);
        }
        else {
            line.setStartX(line.getOwnerBall().getCenterX());
            line.setStartY(line.getOwnerBall().getCenterY());
        }
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (this.equals(firstOne))
                    nextTime = getRandom();
                setCycleDuration(Duration.seconds(nextTime));
                velocity*=-1;
                play();
            }
        });
    }
}
