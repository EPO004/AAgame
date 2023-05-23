package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import view.Game;
import view.LoginMenu;
import view.MainMenu;

public class Pause {
    public void resume(MouseEvent mouseEvent) {
        Game.getPopUpStage().close();
        Game.getTimeline().play();
         Game.getWindDegree().play();
        Game.getCenterDisk().playTurning();
        Game.getBall().requestFocus();
    }

    public void restart(MouseEvent mouseEvent) throws Exception {
        Game game = new Game();
        Game.getPopUpStage().close();
        Game.getAudioClip().stop();
        if (Game.getWindDegree()!=null) Game.getWindDegree().stop();
        Game.setWindDegree(null);
        MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getRealBalls());
        game.start(LoginMenu.getStage());
    }

    public void exit(MouseEvent mouseEvent) throws Exception {
        Game.getPopUpStage().close();
        Game.getAudioClip().stop();
        Game.setWindDegree(null);
        MainMenu.getUser().getGameSetting().setAllBalls(MainMenu.getUser().getGameSetting().getRealBalls());
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
        mainMenu.start(LoginMenu.getStage());

    }
    @FXML
    private Button music;
    @FXML
    public void initialize(){
        if (MainMenu.getUser().getGameSetting().isMusicOn())
            music.setText("Music : On");
        else
            music.setText("Music : Off");
    }

    public void music(MouseEvent mouseEvent) {
        if (MainMenu.getUser().getGameSetting().isMusicOn()){
            MainMenu.getUser().getGameSetting().setMusicOn(false);
            Game.getAudioClip().stop();
        }
        else {
            MainMenu.getUser().getGameSetting().setMusicOn(true);
            Game.getAudioClip().play();
        }
        initialize();
    }

    public void track1(MouseEvent mouseEvent) {
        if (!MainMenu.getUser().getGameSetting().isMusicOn()) return;
        if (Game.getAudioClip().getSource().contains("track1")) return;
        Game.getAudioClip().stop();
        Game.setAudioClip(new AudioClip(getClass().getResource("/sound/track1.mp3").toExternalForm()));
        Game.getAudioClip().setCycleCount(-1);
        Game.getAudioClip().play();
    }

    public void track2(MouseEvent mouseEvent) {
        if (!MainMenu.getUser().getGameSetting().isMusicOn()) return;
        if (Game.getAudioClip().getSource().contains("track2")) return;
        Game.getAudioClip().stop();
        Game.setAudioClip(new AudioClip(getClass().getResource("/sound/track2.mp3").toExternalForm()));
        Game.getAudioClip().setCycleCount(-1);
        Game.getAudioClip().play();
    }

    public void track3(MouseEvent mouseEvent) {
        if (!MainMenu.getUser().getGameSetting().isMusicOn()) return;
        if (Game.getAudioClip().getSource().contains("track3")) return;
        Game.getAudioClip().stop();
        Game.setAudioClip(new AudioClip(getClass().getResource("/sound/track3.mp3").toExternalForm()));
        Game.getAudioClip().setCycleCount(-1);
        Game.getAudioClip().play();
    }
}
