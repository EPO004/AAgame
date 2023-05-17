package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;

import java.net.URL;
import java.util.Locale;

public class MainMenu extends Application {
    private static Stage stage;
    private static AudioClip audioClip;
    public static boolean isPlaying = false;
    public static boolean onMusic=true;
    private static User user;

    public MainMenu(boolean onMusic, User user) {
        MainMenu.onMusic = onMusic;
        MainMenu.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception {
        audioClip = new AudioClip(getClass().getResource("/sound/mainMenu.mp3").toExternalForm());
        audioClip.setCycleCount(-1);
        if (onMusic && !isPlaying) audioClip.play();
        URL url = MainMenu.class.getResource("/fxml/mainMenu.fxml");
        ImageView backGround = new ImageView(new Image(MainMenu.class.getResource("/image/mainMenu.png").toExternalForm(), 800, 600, false, false));
        Pane pane = new Pane();
        pane.getChildren().addAll(backGround);
        BorderPane borderPane = FXMLLoader.load(url);
        pane.getChildren().addAll(borderPane);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public boolean isOnMusic() {
        return onMusic;
    }

    public static User getUser() {
        return user;
    }
    public static void musicStop(){
        audioClip.stop();
        onMusic=false;
    }
    public static void musicPlay(){
        audioClip.play();
        isPlaying=true;
        onMusic=true;
    }
    public static void exitForMusic(){
        onMusic=true;
        isPlaying=false;
        audioClip.stop();
    }

    public static void setUser(User user) {
        MainMenu.user = user;
    }
}
