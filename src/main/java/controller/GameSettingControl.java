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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;
import view.GameSettingMenu;
import view.LoginMenu;
import view.MainMenu;

import java.io.*;

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
    private Label mapBalls;
    @FXML 
    private Button blackWhite;
    @FXML
    private Label mapFormat;
    public void back(MouseEvent mouseEvent) throws Exception {
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
        mainMenu.start(LoginMenu.getStage());
        saveToJason(MainMenu.getUser());
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
        mapFormat.setText("Map Format "+MainMenu.getUser().getGameSetting().getMapFormat());
        mapBalls.setText("Map Balls : "+MainMenu.getUser().getGameSetting().getMapBallFormat());
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
        user.getGameSetting().changeRealBalls(1);
        initialize();
    }

    public void ballDecrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getAllBalls() == 5) return;
        user.getGameSetting().changeBalls(-1);
        user.getGameSetting().changeRealBalls(-1);
        initialize();
    }

    public void musicState(MouseEvent mouseEvent) throws Exception {
        User user = MainMenu.getUser();
        if (user.getGameSetting().isMusicOn()) {
            user.getGameSetting().setMusicOn(false);
            MainMenu.musicStop();
        }
        else {
            user.getGameSetting().setMusicOn(true);
            MainMenu.musicPlay();
        }
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

    public void mapDecrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getMapFormat()==1) return;
        user.getGameSetting().changeFormat(-1);
        initialize();
    }

    public void mapIncrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getMapFormat()==4) return;
        user.getGameSetting().changeFormat(1);
        initialize();
    }

    public void mapBallDecrease(MouseEvent mouseEvent) {User user = MainMenu.getUser();
        if (user.getGameSetting().getMapBallFormat()==5) return;
        user.getGameSetting().changeMapBall(-1);
        initialize();
    }

    public void mapBallIncrease(MouseEvent mouseEvent) {
        User user = MainMenu.getUser();
        if (user.getGameSetting().getMapBallFormat()==10) return;
        user.getGameSetting().changeMapBall(+1);
        initialize();
    }
    private void saveToJason(User user) throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(user.getUsername())){
                JSONObject setting = new JSONObject();
                setting.put("difficulty", user.getGameSetting().getDifficulty());
                setting.put("ball", user.getGameSetting().getRealBalls());
                setting.put("format", user.getGameSetting().getMapFormat());
                setting.put("map", user.getGameSetting().getMapBallFormat());
                setting.put("music", user.getGameSetting().isMusicOn());
                setting.put("sound", user.getGameSetting().isSoundOn());
                setting.put("shoot", user.getGameSetting().getKeys()[0]);
                setting.put("freeze", user.getGameSetting().getKeys()[1]);
                setting.put("right", user.getGameSetting().getKeys()[2]);
                setting.put("left", user.getGameSetting().getKeys()[3]);
                setting.put("color", user.getGameSetting().getIsBlackWhite());
                temp.put("setting", setting);
            }
            ((JSONObject) j).put("user", temp);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change(MouseEvent mouseEvent) {
        GameSettingMenu.changeMenu();
    }
}
