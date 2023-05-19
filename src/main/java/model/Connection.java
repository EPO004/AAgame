package model;

import javafx.scene.shape.Line;
import view.animations.TurningTransition;

public class Connection extends Line {
    private TurningTransition transition;
    public Connection(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    public TurningTransition getTransition() {
        return transition;
    }

    public void setTransition(TurningTransition transition) {
        this.transition = transition;
    }
}
