package view.animations;

import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import view.Game;
import view.MainMenu;

import java.util.Random;

public class WindDegree extends Transition {
    public int angle = 50;
    public WindDegree(){
        this.setCycleDuration(Duration.millis((5f/MainMenu.getUser().getGameSetting().getWindVelocity())*1000f));
        Random random = new Random();
        angle = random.nextInt(31) - 15;
        Game.getGameControl().setWindString("Wind Degree : " + angle);
    }
    @Override
    protected void interpolate(double frac) {
        boolean ballCount = MainMenu.getUser().getGameSetting().getAllBalls() <= MainMenu.getUser().getGameSetting().getRealBalls()/4;
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Random random = new Random();
                int number = random.nextInt(31) - 15;
                angle = number;
                Game.getGameControl().setWindString("Wind Degree : " + angle);
                setCycleDuration(Duration.millis((5f/MainMenu.getUser().getGameSetting().getWindVelocity())*1000f));
                play();
            }
        });
    }
    public void setDuration(){
        this.stop();
        setCycleDuration(Duration.seconds(0));
    }
}
