package controller;

import com.dlsc.formsfx.model.structure.Field;
import com.sun.javafx.scene.control.IntegerField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.GameSetting;
import model.User;
import org.w3c.dom.Text;
import view.GameSettingMenu;
import view.LoginMenu;
import view.MainMenu;

public class GameSettingControl {
    @FXML
    private Button sound;
    @FXML
    private Label difficulty;
    @FXML
    private Label allBalls;
    @FXML
    private Button music;
    @FXML 
    private Button blackWhite;

    public void back(MouseEvent mouseEvent) throws Exception {
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
        mainMenu.start(LoginMenu.getStage());
    }

    public void diffDecrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getDifficulty() == 1) return;
        user.getGameSetting().changeDifficulty(-1);
        initialize();
    }

    public void diffIncrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getDifficulty() == 3) return;
        user.getGameSetting().changeDifficulty(1);
        initialize();
    }
    @FXML
    public void initialize(){
        difficulty.setText("Difficulty : "+MainMenu.getUser().getGameSetting().getDifficulty());
        allBalls.setText("Balls : "+ MainMenu.getUser().getGameSetting().getAllBalls());
        if (MainMenu.getUser().getGameSetting().isMusicOn())
            music.setText("Music : On");
        else
            music.setText("Music : Off");
        if (MainMenu.getUser().getGameSetting().isSoundOn())
            sound.setText("Sound : On");
        else
            sound.setText("Sound : Off");
        if (MainMenu.getUser().getGameSetting().getIsBlackWhite()==0)
            blackWhite.setText("Black White : Off");
        else
            blackWhite.setText("Black White : On");
    }

    public void ballIncrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getAllBalls() == 50) return;
        user.getGameSetting().changeBalls(1);
        initialize();
    }

    public void ballDecrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getAllBalls() == 5) return;
        user.getGameSetting().changeBalls(-1);
        initialize();
    }

    public void musicState(MouseEvent mouseEvent) throws Exception {
        User user = MainMenu.getUser();
        if (user.getGameSetting().isMusicOn())
            user.getGameSetting().setMusicOn(false);
        else
            user.getGameSetting().setMusicOn(true);
        initialize();
        makeChanges();
    }

    public void soundState(MouseEvent mouseEvent) throws Exception {
        User user = MainMenu.getUser();
        if (user.getGameSetting().isSoundOn())
            user.getGameSetting().setSoundOn(false);
        else
            user.getGameSetting().setSoundOn(true);
        initialize();
        makeChanges();
    }

    public void blackWhiteState(MouseEvent mouseEvent) throws Exception {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getIsBlackWhite()==0)
            user.getGameSetting().setIsBlackWhite(-1);
        else
            user.getGameSetting().setIsBlackWhite(0);
        initialize();
        makeChanges();
    }
    public void makeChanges() throws Exception {
        GameSettingMenu gameSettingMenu = new GameSettingMenu();
        gameSettingMenu.start(LoginMenu.getStage());
    }
}
