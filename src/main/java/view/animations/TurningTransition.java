package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;
import model.CenterDisk;

import java.util.Timer;

public class TurningTransition extends Transition {
    private Node node;
    double pivotX;
    double pivotY;
    double x;
    double y;
    boolean isLine;
    public static double angle=0;
    Ball ball;
    double velocity;
    public TurningTransition(Node node, double pivotX, double pivotY, double velocity){
        this.node = node;
        this.velocity = velocity;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        ball = (Ball) node;
        x = 400f;
        y = 300f;
        setCycleDuration(Duration.INDEFINITE);
        setCycleCount(-1);
    }

    public TurningTransition(Node node, double pivotX, double pivotY, double velocity, boolean isLine) {
        this.node = node;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.isLine = isLine;
        this.velocity = velocity;
        setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        Rotate rotate = new Rotate();
        rotate.setPivotX(pivotX);
        rotate.setPivotY(pivotY);
        rotate.setAngle(velocity);
        node.getTransforms().add(rotate);
        Timer timer = new Timer();
        //stop();
        if (ball!=null) {
            ball.setX(400f + Math.cos(Math.toDegrees(angle)) * 150f);
            ball.setY(300f + Math.sin(Math.toDegrees(angle)) * 150f);
        }
    }
}
