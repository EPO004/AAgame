package view.animations;

import javafx.animation.Transition;
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
    private double angle=270;
    Ball ball;
    double velocity;
    public TurningTransition(Node node, double pivotX, double pivotY, double velocity){
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
        this.node = node;
        this.velocity = velocity;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        ball = (Ball) node;
        x = ball.getCenterX();
        y = 300f;
    }

    public TurningTransition(Node node, double pivotX, double pivotY, double velocity, boolean isLine) {
        this.setCycleDuration(Duration.INDEFINITE);
        this.setCycleCount(-1);
        this.node = node;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.isLine = isLine;
        this.velocity = velocity;
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
        Timer timer = new Timer();
        //stop();
     //   ball.setCenterX(x + Math.cos(angle)*150);
       // ball.setCenterY(y + Math.sin(angle)*150);
      //  System.out.println(ball.getCenterX()+" : "+ball.getCenterY());
    }
}
