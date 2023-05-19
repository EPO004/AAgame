package model;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.MainMenu;
import view.animations.TurningTransition;

public class Ball extends Circle {
    private TurningTransition transition;
    private Circle ball;
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

    public TurningTransition getTransition() {
        return transition;
    }

    public void setTransition(TurningTransition transition) {
        this.transition = transition;
    }
}
