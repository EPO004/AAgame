package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GameSetting;
import java.net.URL;

public class GameSettingMenu extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = GameSettingMenu.class.getResource("/fxml/gameSetting.fxml");
        Pane pane = new Pane();
        ImageView background = new ImageView(new Image(GameSettingMenu.class.getResource("/image/gameSetting.jpg").toExternalForm(), 800, 600, false, false));
        pane.getChildren().addAll(background);
        BorderPane borderPane = FXMLLoader.load(url);
        pane.getChildren().addAll(borderPane);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        stage = primaryStage;
        primaryStage.setScene(new Scene(pane));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    public static void changeMenu(){
        Stage change = new Stage(StageStyle.TRANSPARENT);
        BorderPane borderPane = new BorderPane();
        Button button = getStyled("Shooting");
        Button button1 = getStyled("Freezing");
        Button button2 = getStyled("Right Moving");
        Button button3 = getStyled("Left Moving");
        Button button4 = getStyled("close");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(button, button1,button2, button3, button4);
        change.initOwner(stage);
        change.initModality(Modality.APPLICATION_MODAL);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        vBox.setEffect(monochrome);
        change.setScene(new Scene(vBox, Color.TRANSPARENT));
        change.show();
        Button[] buttons = {button, button1, button2, button3};
        change(buttons);
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                change.close();
            }
        });
    }
    private static Button getStyled(String name){
        Button button = new Button(name);
        button.getStylesheets().add(Game.class.getResource("/css/game.css").toExternalForm());
        button.getStyleClass().add("Button");
        return button;
    }
    private static void change(Button[] buttons){
        GameSetting gameSetting = MainMenu.getUser().getGameSetting();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Key changed");
        shoot(buttons[0], alert, gameSetting);
        freeze(buttons[1], alert, gameSetting);
        right(buttons[2], alert, gameSetting);
        left(buttons[3], alert, gameSetting);
    }
    private static void shoot(Button button, Alert alert, GameSetting gameSetting){
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        alert.setContentText("You changed shooting to "+ event.getCode().getName());
                        if (alert.showAndWait().get().getButtonData().isCancelButton()) return;
                        gameSetting.getKeys()[0] = event.getCode().getName()+"";
                    }
                });
            }
        });
    }
    private static void freeze(Button button, Alert alert, GameSetting gameSetting){
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        alert.setContentText("You changed freezing to "+ event.getCode().getName());
                        if (alert.showAndWait().get().getButtonData().isCancelButton()) return;
                        gameSetting.getKeys()[1] = event.getCode().getName()+"";
                    }
                });
            }
        });
    }
    private static void right(Button button, Alert alert, GameSetting gameSetting){
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        alert.setContentText("You changed right moving to "+ event.getCode().getName());
                        if (alert.showAndWait().get().getButtonData().isCancelButton()) return;
                        gameSetting.getKeys()[2] = event.getCode().getName()+"";
                    }
                });
            }
        });
    }
    private static void left(Button button, Alert alert, GameSetting gameSetting){
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        alert.setContentText("You changed left moving to "+ event.getCode().getName());
                        if (alert.showAndWait().get().getButtonData().isCancelButton()) return;
                        gameSetting.getKeys()[3] = event.getCode().getName()+"";
                    }
                });
            }
        });
    }
}
