package model;

import view.animations.Phase2Transition;
import view.animations.RadiusChange;
import view.animations.TurningTransition;

import java.util.ArrayList;

public class LastGame {
    private ArrayList<Ball> balls;
    private int remainBalls;
    private boolean turningTransition;
    private boolean phase2Transition;
    private boolean radiusChange;
    private boolean visibleTransition;
    private int minute;
    private int second;
    private double freeze;

    public LastGame(ArrayList<Ball> balls, int remainBalls, int minute, int second, double freeze) {
        this.balls = balls;
        this.remainBalls = remainBalls;
        this.minute = minute;
        this.second = second;
        this.freeze = freeze;
    }

    public LastGame() {
    }

    public void setTurningTransition(boolean turningTransition) {
        this.turningTransition = turningTransition;
    }

    public void setPhase2Transition(boolean phase2Transition) {
        this.phase2Transition = phase2Transition;
    }

    public void setRadiusChange(boolean radiusChange) {
        this.radiusChange = radiusChange;
    }

    public void setVisibleTransition(boolean visibleTransition) {
        this.visibleTransition = visibleTransition;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public int getRemainBalls() {
        return remainBalls;
    }

    public boolean isTurningTransition() {
        return turningTransition;
    }

    public boolean isPhase2Transition() {
        return phase2Transition;
    }

    public boolean isRadiusChange() {
        return radiusChange;
    }

    public boolean isVisibleTransition() {
        return visibleTransition;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public double getFreeze() {
        return freeze;
    }
    public void addBall(Ball ball){
        if(balls==null) balls = new ArrayList<>();
        balls.add(ball);
    }

    public void setRemainBalls(int remainBalls) {
        this.remainBalls = remainBalls;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setFreeze(double freeze) {
        this.freeze = freeze;
    }
}
