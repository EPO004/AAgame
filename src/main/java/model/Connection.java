package model;

import javafx.animation.Transition;
import javafx.scene.shape.Line;
import view.animations.TurningTransition;
import view.animations.VisibleAnimation;

public class Connection extends Line {
    private Transition transition;
    private VisibleAnimation visibleAnimation;

    public VisibleAnimation getVisibleAnimation() {
        return visibleAnimation;
    }

    public void setVisibleAnimation(VisibleAnimation visibleAnimation) {
        this.visibleAnimation = visibleAnimation;
    }

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
