package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.User;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.AvatarMenu;
import view.LoginMenu;
import view.MainMenu;
import view.ProfileMenu;

import java.io.*;
import java.util.Random;

public class AvatarChoosingControl {
    public static boolean isProfile;
    public static void selectedSuccessfully(String avatarUrl, User user) throws Exception {
      //  System.out.println(avatarUrl);
        saveUrlToJson(user, avatarUrl);
        user.setAvatarUrl(avatarUrl);
        if (!isProfile) {
            MainMenu mainMenu = new MainMenu(MainMenu.onMusic, user);
            mainMenu.start(LoginMenu.getStage());
        }
        else {
            ProfileMenu profileMenu = new ProfileMenu(user);
            profileMenu.start(LoginMenu.getStage());
        }
    };
    private static void saveUrlToJson(User user, String avatarUrl) throws IOException, ParseException {
        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        for (Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            if (temp.get("username").equals(user.getUsername())){
                temp.put("avatarUrl", avatarUrl);
            }
            ((JSONObject) j).put("user", temp);
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomAvatar(MouseEvent mouseEvent) throws Exception {
        Random random = new Random();
        int number = random.nextInt(10-1)+1;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Avatar Selected");
        alert.setContentText("Are you sure to choose avatar "+number+"?");
        if ( alert.showAndWait().get().getButtonData().isCancelButton()) return;
        selectedSuccessfully(AvatarChoosingControl.class.getResource("/image/avatar"+number+".png").toString(), LoginMenuControl.getUser());
    }

    public void chooseMyAvatar(MouseEvent mouseEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(LoginMenu.getStage());
        if (file == null) return;
        String s = "file:/"+file.toString().replaceAll("\\\\", "/");
        selectedSuccessfully(s, LoginMenuControl.getUser());
    }

}
