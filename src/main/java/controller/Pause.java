package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import model.Ball;
import model.LastGame;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.Game;
import view.LoginMenu;
import view.MainMenu;
import view.animations.Phase2Transition;
import view.animations.TurningTransition;

import java.io.*;
import java.util.ArrayList;

public class Pause {
    public void resume(MouseEvent mouseEvent) {
        Game.getPopUpStage().close();
        Game.getTimeline().play();
        if (Game.getWindDegree()!=null)Game.getWindDegree().play();
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

    public void save(MouseEvent mouseEvent) throws IOException, ParseException {
        if (MainMenu.getUser().getPassword()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Resume Failed");
            alert.setContentText("You don't have any account,\n please first register then try.");
            alert.showAndWait();
            return;
        }
        LastGame lastGame = new LastGame(Game.getCenterDisk().getCenterDisk(),
                MainMenu.getUser().getGameSetting().getAllBalls(),
                Integer.parseInt(Game.getGameControl().getTime().substring(12, 14)),
                Integer.parseInt(Game.getGameControl().getTime().substring(15, 17)),
                Game.getGameControl().getFreezeProgress());
        if (Game.getCenterDisk().getCenterDisk().get(0).getTransition() instanceof TurningTransition) lastGame.setTurningTransition(true);
        if (Game.getCenterDisk().getCenterDisk().get(0).getTransition() instanceof Phase2Transition) lastGame.setPhase2Transition(true);
        if (Game.getCenterDisk().getCenterDisk().get(0).getVisibleAnimation()!=null) lastGame.setVisibleTransition(true);
        if (Game.getCenterDisk().getCenterDisk().get(0).getRadiusChange()!=null) lastGame.setRadiusChange(true);
        MainMenu.getUser().setLastGame(lastGame);
        saveToJson(lastGame);
    }
    private void saveToJson(LastGame lastGame) throws IOException, ParseException {
        User user = MainMenu.getUser();
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(user.getUsername())){
                JSONObject jsonObject = getJson(lastGame);
                temp.put("resume", jsonObject);
            }
            ((JSONObject) j).put("user", temp);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private JSONObject getJson(LastGame lastGame){
        JSONObject jsonObject = new JSONObject();
        JSONObject ball = new JSONObject();
        ArrayList<Ball> balls = lastGame.getBalls();
        int countOfBall = balls.size();
        for(int i=0; i<balls.size(); i++){
            JSONObject coordinate = new JSONObject();
            coordinate.put("x", balls.get(i).getCenterX());
            coordinate.put("angle", balls.get(i).getAngle());
            coordinate.put("y", balls.get(i).getCenterY());
            ball.put("ball"+i, coordinate);
        }
        jsonObject.put("balls", ball);
        jsonObject.put("remainBalls", lastGame.getRemainBalls());
        jsonObject.put("phase1", lastGame.isTurningTransition());
        jsonObject.put("phase2", lastGame.isPhase2Transition());
        jsonObject.put("radius", lastGame.isRadiusChange());
        jsonObject.put("visible", lastGame.isVisibleTransition());
        jsonObject.put("minute", lastGame.getMinute());
        jsonObject.put("second", lastGame.getSecond());
        jsonObject.put("freeze", lastGame.getFreeze());
        jsonObject.put("count", countOfBall);
        return jsonObject;
    }
}
