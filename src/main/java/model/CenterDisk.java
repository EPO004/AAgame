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
import view.animations.VisibleAnimation;

import java.util.ArrayList;
import java.util.Random;

public class CenterDisk extends Circle {
    private ArrayList<Ball> centerDisk;
    private ArrayList<Connection> lines = new ArrayList<>();
    private Connection line;
    public CenterDisk () throws InterruptedException {
        super(400f, 300f, 70f);
        centerDisk = new ArrayList<>();
        setDefaultBalls();
    }

    public ArrayList<Ball> getCenterDisk() {
        return centerDisk;
    }
    public void addBall(Ball ball){
        phase1(ball);
    }
    public void stopTurning(){
        for (int i=0; i<centerDisk.size(); i++){
            Ball ball =(Ball)centerDisk.get(i);
            ball.getTransition().stop();
            if (ball.getRadiusChange()!=null) ball.getRadiusChange().stop();
            if (ball.getVisibleAnimation()!=null) ball.getVisibleAnimation().stop();
            if (lines.get(i).getVisibleAnimation()!=null) lines.get(i).getVisibleAnimation().stop();
            lines.get(i).getTransition().stop();
        }
    }
    public void playTurning(){
        for (int i=0; i<centerDisk.size(); i++){
            Ball ball =(Ball)centerDisk.get(i);
            ball.getTransition().play();
            if (ball.getRadiusChange()!=null) ball.getRadiusChange().play();
            if (ball.getVisibleAnimation()!=null) ball.getVisibleAnimation().play();
            if (lines.get(i).getVisibleAnimation()!=null) lines.get(i).getVisibleAnimation().play();
            lines.get(i).getTransition().play();
        }
        for (int i=0; i<lines.size(); i++){
            Connection line =(Connection)lines.get(i);
            line.getTransition().play();
            if (line.getVisibleAnimation()!=null) line.getVisibleAnimation().play();
        }
    }
    private void setDefaultBalls() throws InterruptedException {
        int allBalls = MainMenu.getUser().getGameSetting().getMapBallFormat();
        int format = MainMenu.getUser().getGameSetting().getMapFormat();
        double angle = format*17;
        for (int i=0; i<allBalls; i++){
            Ball ball = new Ball(400f+Math.cos(Math.toRadians(angle*i))*150f, 300f+Math.sin(Math.toRadians(angle*i))*150f);
            line = new Connection(ball.getCenterX(), ball.getCenterY(), 400f, 300f);
            line.setFill(Color.BLACK);
            TurningTransition transition1 = new TurningTransition( line, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, true);
            TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, (double) (angle*i));
            transition.play();
            transition1.play();
            lines.add(line);
            line.setOwnerBall(ball);
            centerDisk.add(ball);
            Game.getPane().getChildren().add(ball);
            Game.getPane().getChildren().add(line);
            ball.setTransition(transition);
            line.setTransition(transition1);
        }
    }
    public void phase1(Ball ball){
        line = new Connection(ball.getCenterX(), ball.getCenterY(), 400f, 300f);
        line.setFill(Color.BLACK);
        TurningTransition transition = new TurningTransition( ball, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, 90);
        TurningTransition transition1 = new TurningTransition( line, 400f, 300f, MainMenu.getUser().getGameSetting().getTurningVelocity()/5, true);
        lines.add(line);
        centerDisk.add(ball);
        line.setOwnerBall(ball);
        Game.getPane().getChildren().add(line);
        Game.getPane().getChildren().add(ball);
        ball.setTransition(transition);
        line.setTransition(transition1);
        transition.play();
        transition1.play();
    }

    public ArrayList<Connection> getLines() {
        return lines;
    }
    public void makeShootedPhase2(){
        Random random = new Random();
        int number = random.nextInt(2)+1;
        int velocity = MainMenu.getUser().getGameSetting().getTurningVelocity()/5;
        if (number%2 == 0) velocity*=-1;
        for (int i=0; i<centerDisk.size(); i++){
                Ball ball = (Ball) centerDisk.get(i);
                double angle;
            if (ball.getTransition() instanceof TurningTransition) {
                    TurningTransition turningTransition = (TurningTransition) ball.getTransition();
                    angle = turningTransition.angle;
                }
            else {
                Phase2Transition phase2Transition = (Phase2Transition) ball.getTransition();
                angle = phase2Transition.angle;
            }
         //   System.out.println(turningTransition.angle);
                Phase2Transition transition = new Phase2Transition( ball, 400f, 300f, velocity, angle);
                ball.setTransition(transition);
                transition.play();
            line = (Connection) lines.get(i);
            Phase2Transition transition1 = new Phase2Transition( line, 400f, 300f,velocity, true);
            transition1.play();
        }
        for (int i=0; i<lines.size(); i++){

        }
    }
    public void phase2RadiusChange(){
        for (int i=0; i<centerDisk.size(); i++){
            Ball ball = (Ball) centerDisk.get(i);
            RadiusChange transition = new RadiusChange( ball);
            ball.setRadiusChange(transition);
            transition.play();
        }
    }
    public void phase3Visible(){
        for (int i=0; i<centerDisk.size(); i++){
            Ball ball = (Ball) centerDisk.get(i);
            VisibleAnimation transition = new VisibleAnimation( ball);
            ball.setVisibleAnimation(transition);
            transition.play();
            line = (Connection) lines.get(i);
            VisibleAnimation transition1 = new VisibleAnimation( line);
            line.setVisibleAnimation(transition1);
            transition1.play();
        }
    }
}
