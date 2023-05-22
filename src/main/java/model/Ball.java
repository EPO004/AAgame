package model;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.MainMenu;
import view.animations.Phase2Transition;
import view.animations.RadiusChange;
import view.animations.TurningTransition;

public class Ball extends Circle {
    private Transition transition;
    private RadiusChange radiusChange;
    private Circle ball;
    private double x = 300f;
    private double y = 400f + 150f;
    public Ball(CenterDisk centerDisk){
        super(centerDisk.getCenterX(), centerDisk.getCenterY()+270, 10);
        this.ball = ball();
    }
    public Ball(CenterDisk centerDisk, boolean turning){
        super(centerDisk.getCenterX(), centerDisk.getCenterY()+150, 10);
        this.ball = ball();
    }
    public Ball(double x, double y){
        super(x, y, 10);
        this.ball = ball();
    }
    private Circle ball(){
        Circle ball = new Circle(this.getCenterX(), this.getCenterY(), this.getRadius());
        Label label = new Label(MainMenu.getUser().getGameSetting().getAllBalls()+"");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 5");
        StackPane stackPane = new StackPane(ball, label);
        return ball;
    }

    public Circle getBall() {
        return ball;
    }

    public Transition getTransition() {
        return transition;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    @Override
    public String toString(){
        String output= "x = "+x+";   y = "+ y;
        return output;
    }

    public RadiusChange getRadiusChange() {
        return radiusChange;
    }

    public void setRadiusChange(RadiusChange radiusChange) {
        this.radiusChange = radiusChange;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }
}
