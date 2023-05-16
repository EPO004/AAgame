package controller;

import javafx.css.Match;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import view.AvatarMenu;
import view.LoginMenu;
import view.MainMenu;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuControl {
    @FXML
    private Label label;
    @FXML
    private TextField username;
    private static User user;
    @FXML
    private PasswordField password;
    @FXML
    public void initialize(){
        username.textProperty().addListener((observable, oldText, newText) -> {
            label.setText("Welcome "+ username.getText()+"!");
        });
    }

    public void register(MouseEvent mouseEvent) throws Exception {
        String regex = "\\w+";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Password error");
        Matcher matcher = Pattern.compile(regex).matcher(password.getText());
        if (password.getText().isEmpty()||username.getText().isEmpty()) return;
        if (User.getUserInfo().get(username.getText())!=null){
            alert.setHeaderText("Username error");
            alert.setContentText("Username is already exist!");
            alert.showAndWait();
            resetFields(username, password);
        }
        else if (!matcher.matches()){
            alert.setContentText("Password is invalid!\nJust use numbers and characters!");
            alert.showAndWait();
            resetFields(username, password);
        }
        else if(password.getText().length()<4){
            alert.setContentText("Password is weak!");
            alert.showAndWait();
            resetFields(username, password);
        }
        else
            validRegister(alert);
    }

    public void login(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Login Failed");
        if (username.getText().isEmpty()||password.getText().isEmpty()) return;
        if (User.getUserInfo().get(username.getText())==null){
            alert.setContentText("Username not found!");
            alert.showAndWait();
            resetFields(username, password);
        }
        else if (!(user = User.getUserInfo().get(username.getText())).passwordMatch(password.getText())){
            alert.setContentText("Password is wrong!");
            alert.showAndWait();
            resetFields(username, password);
        }
        else {
            resetFields(this.username, this.password);
            MainMenu mainMenu = new MainMenu(MainMenu.onMusic, user);
            mainMenu.start(LoginMenu.getStage());
        }
    }

    public void skip(MouseEvent mouseEvent) throws Exception {
        resetFields(this.username, this.password);
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, null);
        mainMenu.start(LoginMenu.getStage());
    }
    public void resetFields(TextField username, PasswordField password){
        username.setText("");
        password.setText("");
    }
    private void validRegister(Alert alert) throws Exception {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Information is correct");
        alert.setContentText("Are you sure to register with this information?");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            resetFields(username, password);
            return;
        }
        user = new User(username.getText(), password.getText());
      //  System.out.println(username.getText());
        saveToJson(username.getText(), password.getText());
        AvatarMenu avatarMenu = new AvatarMenu();
        resetFields(username, password);
        AvatarChoosingControl.isProfile = false;
        avatarMenu.start(LoginMenu.getStage());
    }
    public void saveToJson(String username, String password) throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("avatarUrl", "");
            json.put("score", 0);
            json.put("playedTime", 0);
            json.put("hard", 0);
            json.put("normal", 0);
            json.put("easy", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject.put("user", json);
        a.add(jsonObject);
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser() {
        return user;
    }
    public static void backUpData() throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) return;
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            User user = new User((String) temp.get("username"), (String) temp.get("password"));
            user.setAvatarUrl((String) temp.get("avatarUrl"));;
            user.setScore(( (Long) temp.get("score")).intValue());
            user.setPlayedTimeSecond(((Long) temp.get("playedTime")).intValue());
            user.setHardPlayed(((Long) temp.get("hard")).intValue());
            user.setNormalPlayed(((Long) temp.get("normal")).intValue());
            user.setEasyPlayed(((Long) temp.get("easy")).intValue());
            User.getUserInfo().put((String) temp.get("username"), user);
        }
    }
}

