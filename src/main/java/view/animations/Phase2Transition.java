package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;

import java.util.Random;
import java.util.Timer;

public class Phase2Transition extends Transition {
    private Node node;
    private static Phase2Transition firstOne;
    double pivotX;
    double pivotY;
    double x;
    public static int number;
    double y;
    private boolean transitionPlaying;
    private int previousTime;
    private static int nextTime = getRandom();
    boolean isLine;
    public static double angle = 0;
    Ball ball;
    double velocity;

    public Phase2Transition(Node node, double pivotX, double pivotY, double velocity) {
        this.node = node;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.velocity = velocity;
        ball = (Ball) node;
        x = ball.getCenterX();
        y = ball.getCenterY();
        setCycleDuration(Duration.seconds(nextTime));
        if (firstOne==null) firstOne = this;
    }
    public Phase2Transition(Node node, double pivotX, double pivotY, double velocity, boolean isLine) {
        this.node = node;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.velocity = velocity;
        setCycleDuration(Duration.seconds(nextTime));
    }

    private static int getRandom(){

        Random random = new Random();
        int number = random.nextInt(6) + 4;
        return number;
    }

    @Override
    protected void interpolate(double frac) {
        Rotate rotate = new Rotate();
        rotate.setPivotX(pivotX);
        rotate.setPivotY(pivotY);
        rotate.setAngle(velocity);
        angle += velocity;
        angle%=360;
        node.getTransforms().add(rotate);
        if (ball!=null) {
            ball.setX(400f + Math.cos(Math.toDegrees(angle)) * 150f);
            ball.setY(300f + Math.sin(Math.toDegrees(angle)) * 150f);
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
