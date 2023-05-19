package controller;

import javafx.scene.input.MouseEvent;
import view.*;

public class MainMenuControl {
    public void exit(MouseEvent mouseEvent) throws Exception {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.getStage());
        MainMenu.exitForMusic();
        MainMenu.setUser(null);
    }

    public void profile(MouseEvent mouseEvent) throws Exception {
        if (MainMenu.onMusic) MainMenu.isPlaying=true;
        ProfileMenu profileMenu = new ProfileMenu(MainMenu.getUser());
        profileMenu.start(LoginMenu.getStage());

    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        if (MainMenu.onMusic) MainMenu.isPlaying=true;
        ScoreBoard scoreBoard = new ScoreBoard(null);
        scoreBoard.start(LoginMenu.getStage());
    }

    public void setting(MouseEvent mouseEvent) throws Exception {
        if (MainMenu.onMusic) MainMenu.isPlaying=true;
        GameSettingMenu gameSettingMenu = new GameSettingMenu();
        gameSettingMenu.start(LoginMenu.getStage());
    }

    public void startGame(MouseEvent mouseEvent) throws Exception {
        if (MainMenu.onMusic) MainMenu.isPlaying=true;
        Game game = new Game();
        game.start(LoginMenu.getStage());
    }
}
