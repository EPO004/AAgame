package model;

import javafx.animation.Transition;
import javafx.scene.shape.Line;
import view.animations.TurningTransition;

public class Connection extends Line {
    private Transition transition;
    public Connection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }
}
