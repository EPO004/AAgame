package controller;

import javafx.scene.layout.Pane;
import model.Ball;
import model.CenterDisk;
import view.MainMenu;
import view.ShootingAnimation;

public class GameControl {
    public static void shoot(Pane pane, CenterDisk centerDisk, Ball ball){
        int balls = MainMenu.getUser().getGameSetting().getAllBalls();
        MainMenu.getUser().getGameSetting().setAllBalls(balls-1);
        Ball ball1 = new Ball(centerDisk);
        pane.getChildren().addAll(ball1);
        ShootingAnimation shootingAnimation = new ShootingAnimation(pane,centerDisk, ball1);
        shootingAnimation.play();
        if (balls==1) {
            pane.getChildren().remove(ball);
        }
    }
}
