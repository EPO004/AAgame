package controller;

import javafx.scene.input.MouseEvent;
import view.LoginMenu;
import view.MainMenu;
import view.ProfileMenu;
import view.ScoreBoard;

public class MainMenuControl {
    public void exit(MouseEvent mouseEvent) throws Exception {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.getStage());
        MainMenu.musicStop();
    }

    public void profile(MouseEvent mouseEvent) throws Exception {
        ProfileMenu profileMenu = new ProfileMenu(MainMenu.getUser());
        profileMenu.start(LoginMenu.getStage());

    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard(null);
        scoreBoard.start(LoginMenu.getStage());
    }
}
