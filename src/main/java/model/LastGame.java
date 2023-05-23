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

    public LastGame(ArrayList<Ball> baUslls, int remainBalls, int minute, int second, double freeze) {
        this.balls = balls;
        this.remainBalls = remainBalls;
        this.minute = minute;
        this.second = second;
        this.freeze = freeze;
    }
}
