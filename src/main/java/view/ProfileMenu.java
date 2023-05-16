package view;

import controller.AvatarChoosingControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class ProfileMenu extends Application {
    private User user;
    public ProfileMenu(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (user==null){
            skippedUSer();
            return;
        }
        Pane pane = new Pane();
        ImageView imageView = new ImageView(new Image(ProfileMenu.class.getResource("/image/profileMenu.jpg").toExternalForm(), 800, 600, false, false));
        pane.getChildren().addAll(imageView);
        ImageView border = new ImageView(new Image(ProfileMenu.class.getResource("/image/border.jpg").toExternalForm(), 270, 180, false, false));
        imageView = new ImageView(new Image(user.getAvatarUrl(), 240, 160, false, false));
        URL url = ProfileMenu.class.getResource("/fxml/profileMenu.fxml");
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(imageView);
        vBox.getChildren().addAll(setInformation());
        imageView.setPickOnBounds(true);
        clickedImage(imageView);
        borderPane.setCenter(vBox);
        BorderPane borderPane2 = FXMLLoader.load(url);
        borderPane2.setTop(borderPane);
        pane.getChildren().addAll(borderPane2);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
    private void skippedUSer(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("profile is empty");
        alert.setContentText("please exit game then register an account");
        alert.showAndWait();
    }
    private void clickedImage(ImageView imageView){
        imageView.setOnMouseClicked((MouseEvent e) -> {
            try {
                AvatarMenu avatarMenu = new AvatarMenu();
                AvatarChoosingControl.isProfile=true;
                avatarMenu.start(LoginMenu.getStage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    private VBox setInformation(){
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(25);
        Label label = new Label("username : "+user.getUsername());
        label.setTextFill(Color.INDIANRED);
        label.setScaleX(3);
        label.setScaleY(3);
        vBox.getChildren().addAll(label);
        label = new Label("password : "+user.getPassword());
        label.setTextFill(Color.INDIANRED);
        label.setScaleX(3);
        label.setScaleY(3);
        vBox.getChildren().addAll(label);
        label = new Label("score : "+user.getScore());
        label.setTextFill(Color.INDIANRED);
        label.setScaleX(3);
        label.setScaleY(3);
        vBox.getChildren().addAll(label);
        return vBox;
    }
}
