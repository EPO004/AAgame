package controller;

import javafx.scene.control.Alert;
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
        MainMenu.getAudioClip().stop();
        Game game = new Game();
        game.start(LoginMenu.getStage());
    }

    public void resume(MouseEvent mouseEvent) throws Exception {
        if (MainMenu.getUser().getPassword()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Resume Failed");
            alert.setContentText("You don't have any account,\n please first register then try.");
            alert.showAndWait();
            return;
        }
        if (MainMenu.getUser().getLastGame()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Loading Game Failed");
            alert.setContentText("You don't have any last game,\n please first start game then save it.");
            alert.showAndWait();
            return;
        }
        MainMenu.getAudioClip().stop();
        Game game = new Game(true);
        game.start(LoginMenu.getStage());
    }
}
