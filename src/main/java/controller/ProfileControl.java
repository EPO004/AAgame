package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.ChangerMenu;
import view.LoginMenu;
import view.MainMenu;
import view.ProfileMenu;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileControl {
    @FXML
    private TextField username;
    @FXML
    private TextField username2;
    @FXML
    private TextField password;
    @FXML
    private TextField password2;
    public void back(MouseEvent mouseEvent) throws Exception {
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
        mainMenu.start(LoginMenu.getStage());
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        MainMenu.musicStop();
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.getStage());
    }

    public void removeAccount(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("remove account");
        alert.setContentText("are you sure to remove you account?");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) return;
        removeData();
        MainMenu.musicStop();
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.getStage());
    }
    private void removeData() throws IOException, ParseException {
        User user = User.getUserInfo().get(MainMenu.getUser().getUsername());
        user.removeUser(user);
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        int index=0, count=0;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            count++;
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(user.getUsername()))
                index=count;
        }
        a.remove(index-1);
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void passwordChange(MouseEvent mouseEvent) throws Exception{
        ChangerMenu changerMenu = new ChangerMenu(false);
        changerMenu.start(LoginMenu.getStage());
    }

    public void usernameChange(MouseEvent mouseEvent) throws Exception {
        ChangerMenu changerMenu = new ChangerMenu(true);
        changerMenu.start(LoginMenu.getStage());
    }

    public void confirmUsername(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("username doesn't change");
        if (username.getText().isEmpty()||username2.getText().isEmpty()) return;
        if (!username.getText().equals(username2.getText())){
            alert.setContentText("usernames doesn't match, please enter them correctly");
            alert.showAndWait();
        }
        else if (User.getUserInfo().get(username.getText())!=null){
            alert.setContentText("username already exist");
            alert.showAndWait();
        }
        else if(username.getText().equals(MainMenu.getUser().getUsername())){
            alert.setContentText("username is not new");
            alert.showAndWait();
        }
        else {
            saveToJson(username.getText(), null);
            User.getUserInfo().remove(MainMenu.getUser().getUsername(), MainMenu.getUser());
            MainMenu.getUser().setUsername(username.getText());
            User.getUserInfo().put(MainMenu.getUser().getUsername(), MainMenu.getUser());
            MainMenu.getUser().setUsername(username.getText());
            runProfile();
        }
    }

    public void cancelUsername(MouseEvent mouseEvent) throws Exception {
        runProfile();
    }
    private void runProfile() throws Exception {
        ProfileMenu profileMenu = new ProfileMenu(MainMenu.getUser());
        profileMenu.start(LoginMenu.getStage());
    }
    private void saveToJson(String username, String password) throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(MainMenu.getUser().getUsername())){
                if (password==null){
                    temp.put("username", username);
                }
                else {
                    temp.put("password", password);
                }
            }
            ((JSONObject) j).put("user", temp);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelPassword(MouseEvent mouseEvent) throws Exception {
        runProfile();
    }

    public void confirmPassword(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String regex = "\\w+";
        Matcher matcher = Pattern.compile(regex).matcher(password.getText());
        alert.setHeaderText("password doesn't change");
        if (password.getText().isEmpty()||password2.getText().isEmpty()) return;
        if (!matcher.matches()){
            alert.setContentText("Password is invalid!\nJust use numbers and characters!");
            alert.showAndWait();
        }
        else if (!password2.getText().equals(password.getText())){
            alert.setContentText("passwords doesn't match, please enter them correctly");
            alert.showAndWait();
        }
        else if(password.getText().length()<4){
            alert.setContentText("Password is weak!");
            alert.showAndWait();
        }
        else if(password.getText().equals(MainMenu.getUser().getPassword())){
            alert.setContentText("password is not new");
            alert.showAndWait();
        }
        else {
            saveToJson(null, password.getText());
            MainMenu.getUser().setPassword(password.getText());
            MainMenu.getUser().setPassword(password.getText());
            runProfile();
        }
    }
}
