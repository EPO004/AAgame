package view.animations;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.Node;
import model.Ball;
import view.Game;
import view.MainMenu;

import java.util.ArrayList;

public class VisibleAnimation extends Transition {
    private Node node;
    private FadeTransition transition;
    private VisibleAnimation visibleAnimation;

    public VisibleAnimation(Node ball) {
        node = ball;
        transition =new FadeTransition();
        setCycleDuration(Duration.seconds(4.1));
        transition.setNode(node);
        transition.setDuration(Duration.seconds(4));
        transition.setFromValue(0);
        transition.setToValue(100);
        transition.play();
        visibleAnimation = this;
    }
    private void makeInVisible(){

    }

    @Override
    protected void interpolate(double frac) {

        ArrayList<Ball> balls = Game.getCenterDisk().getCenterDisk();
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i =0; i<balls.size(); i++){
                    for (int j=i+1; j<balls.size(); j++){
                        Ball a = balls.get(i);
                        Ball b = balls.get(j);
                        if (a.getBoundsInParent().intersects(b.getBoundsInParent())){
                            Game.getCenterDisk().stopTurning();
                            Game.getPane().setStyle("-fx-background-color: red");
                            MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getAllBalls()+1);
                            Game.getTimeline().stop();
                            Game.endGame();
                            return;
                        }
                    }
                }
                transition.setDuration(Duration.seconds(4));
                visibleAnimation.setCycleDuration(Duration.seconds(4.1));
                visibleAnimation.play();
                transition.play();
            }
        });
    }
}
