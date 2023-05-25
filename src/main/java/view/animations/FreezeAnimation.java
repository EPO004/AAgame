package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import model.CenterDisk;
import view.Game;
import view.MainMenu;

public class FreezeAnimation extends Transition {
    private double readRadius;
    private CenterDisk centerDisk;
    public FreezeAnimation(CenterDisk centerDisk){
        readRadius = centerDisk.getRadius();
        this.centerDisk = centerDisk;
        setCycleDuration(Duration.seconds(MainMenu.getUser().getGameSetting().getFreezeSecond()));
    }
    @Override
    protected void interpolate(double frac) {
        if ( centerDisk.getRadius()==70)centerDisk.setRadius(centerDisk.getRadius()+5);
        else centerDisk.setRadius(centerDisk.getRadius()-5);
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                centerDisk.setRadius(readRadius);
            }
        });

    }
}
