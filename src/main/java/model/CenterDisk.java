package model;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.w3c.dom.Text;
import view.Game;
import view.MainMenu;
import view.animations.Phase2Transition;
import view.animations.RadiusChange;
import view.animations.TurningTransition;

import java.util.ArrayList;
import java.util.Random;

public class CenterDisk extends Circle {
    private Group centerDisk;
    private Group lines = new Group();
    private Connection line;
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
        phase1(ball);
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
            if (ball.getRadiusChange()!=null) ball.getRadiusChange().stop();
        }
        for (int i=0; i<lines.getChildren().size(); i++){
            Connection line =(Connection)lines.getChildren().get(i);
            line.getTransition().stop();
        }
    }
    public void playTurning(){
        for (int i=1; i<centerDisk.getChildren().size(); i++){
            Ball ball =(Ball)centerDisk.getChildren().get(i);
            ball.getTransition().play();
            if (ball.getRadiusChange()!=null) ball.getRadiusChange().play();
        }
        for (int i=0; i<lines.getChildren().size(); i++){
            Connection line =(Connection)lines.getChildren().get(i);
            line.getTransition().play();
        }
    }
    private void setDefaultBalls() throws InterruptedException {
        int allBalls = MainMenu.getUser().getGameSetting().getMapBallFormat();
        int format = MainMenu.getUser().getGameSetting().getMapFormat();
        double angle = format*7;
        for (int i=1; i<=allBalls; i++){
            Ball ball = new Ball(400+Math.cos(angle*i)*150, 300+Math.sin(angle*i)*150);
            line = new Connection(ball.getCenterX(), ball.getCenterY(), 400f, 300f);
            line.setFill(Color.BLACK);
            TurningTransition transition1 = new TurningTransition( line, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, true);
            transition1.play();
            TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5);
            transition.play();
            lines.getChildren().add(line);
            centerDisk.getChildren().addAll(ball);
            ball.setTransition(transition);
            line.setTransition(transition1);
        }
    }
    public void phase1(Ball ball){
        line = new Connection(ball.getCenterX(), ball.getCenterY(), 400f, 300f);
        line.setFill(Color.BLACK);
        TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5);
        TurningTransition transition1 = new TurningTransition( line, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, true);
        lines.getChildren().add(line);
        centerDisk.getChildren().addAll(ball);
        ball.setTransition(transition);
        line.setTransition(transition1);
        transition.play();
        transition1.play();
    }

    public Group getLines() {
        return lines;
    }
    public void makeShootedPhase2(){
        Random random = new Random();
        int number = random.nextInt(2)+1;
        int velocity = MainMenu.getUser().getGameSetting().getTurningVelocity()/5;
        if (number%2 == 0) velocity*=-1;
        for (int i=1; i<centerDisk.getChildren().size(); i++){
                Ball ball = (Ball) centerDisk.getChildren().get(i);
                Phase2Transition transition = new Phase2Transition( ball, 400f, 300f, velocity);
                ball.setTransition(transition);
                transition.play();
                line = (Connection) lines.getChildren().get(i-1);
                Phase2Transition transition1 = new Phase2Transition( line, 400f, 300f,velocity, true);
                line.setTransition(transition1);
                transition1.play();
        }
    }
    public void phase2RadiusChange(){
        for (int i=1; i<centerDisk.getChildren().size(); i++){
            Ball ball = (Ball) centerDisk.getChildren().get(i);
            RadiusChange transition = new RadiusChange( ball);
            ball.setRadiusChange(transition);
            transition.play();
        }
    }
}
