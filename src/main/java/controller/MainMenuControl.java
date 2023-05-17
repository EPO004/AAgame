package controller;

import javafx.scene.input.MouseEvent;
import view.*;

public class MainMenuControl {
    public void exit(MouseEvent mouseEvent) throws Exception {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.getStage());
        MainMenu.musicStop();
        MainMenu.setUser(null);
    }

    public void profile(MouseEvent mouseEvent) throws Exception {
        ProfileMenu profileMenu = new ProfileMenu(MainMenu.getUser());
        profileMenu.start(LoginMenu.getStage());

    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard(null);
        scoreBoard.start(LoginMenu.getStage());
    }

    public void setting(MouseEvent mouseEvent) throws Exception {
        GameSettingMenu gameSettingMenu = new GameSettingMenu();
        gameSettingMenu.start(LoginMenu.getStage());
    }
}
