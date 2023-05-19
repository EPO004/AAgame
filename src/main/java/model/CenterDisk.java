package model;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.w3c.dom.Text;
import view.MainMenu;
import view.animations.TurningTransition;

import java.util.ArrayList;

public class CenterDisk extends Circle {
    private Group centerDisk;
    public CenterDisk () throws InterruptedException {
        super(400f, 300f, 70f);
        centerDisk = new Group();
        CenterDisk();
        this.setFill(new ImagePattern(new Image(getClass().getResource("/image/avatar1.png").toExternalForm())));
    }
    private void CenterDisk() throws InterruptedException {
        Circle centerDisk = new Circle(this.getCenterX(), this.getCenterY(), this.getRadius());
        this.centerDisk.getChildren().addAll(centerDisk);
        setDefaultBalls();
    }

    public Group getCenterDisk() {
        return centerDisk;
    }
    public void addBall(Ball ball){
        TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5);
        transition.play();
        centerDisk.getChildren().addAll(ball);
        ball.setTransition(transition);
    }
    public ArrayList getBall(){
        ArrayList <Ball> balls = new ArrayList<>();
        for (int i=1; i<centerDisk.getChildren().size(); i++){
            balls.add((Ball) centerDisk.getChildren().get(i));
        }
        return balls;
    }
    public void stopTurning(){
        for (int i=1; i<centerDisk.getChildren().size(); i++){
            Ball ball =(Ball)centerDisk.getChildren().get(i);
            ball.getTransition().stop();
        }
    }
    public void playTurning(){
        for (int i=1; i<centerDisk.getChildren().size(); i++){
            Ball ball =(Ball)centerDisk.getChildren().get(i);
            ball.getTransition().play();
        }
    }
    private void setDefaultBalls() throws InterruptedException {
        int allBalls = MainMenu.getUser().getGameSetting().getMapBallFormat();
        int format = MainMenu.getUser().getGameSetting().getMapFormat();
        double angle = format*7;
        for (int i=1; i<=allBalls; i++){
            Ball ball = new Ball(400+Math.cos(angle*i)*150, 300+Math.sin(angle*i)*150);
            TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5);
            transition.play();
            centerDisk.getChildren().addAll(ball);
            ball.setTransition(transition);
        }
    }
}
