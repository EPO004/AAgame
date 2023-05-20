package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreBoard extends Application {
    private ArrayList<User> users;

    public ScoreBoard(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = ScoreBoard.class.getResource("/fxml/ScoreBoard.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        Pane pane = new Pane();
        borderPane.setCenter(tableView());
        pane.getChildren().addAll(borderPane);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(MainMenu.getUser().getGameSetting().getIsBlackWhite());
        pane.setEffect(monochrome);
        primaryStage.setScene(new Scene(pane));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    private TableView tableView(){
        TableView tableView = tableDetails();
        ObservableList<Map<String, Object>> items =
                FXCollections.<Map<String, Object>>observableArrayList();
        try {
            for (int i=0; i<10&&i< users.size(); i++){
                Map<String, Object> item1 = hashMapDetail(i);
                items.add(item1);
            }
        }catch (Exception e){

        }
        tableView.setPlaceholder(
                new Label("No players to show, please press buttons"));
        tableView.getItems().addAll(items);
        tableView.getStylesheets().add(ScoreBoard.class.getResource("/css/table.css").toExternalForm());
        return tableView;
    }
    private TableView tableDetails(){
        TableView tableView = new TableView();
        TableColumn<Map, String> rankColumn = new TableColumn<>("rank");
        rankColumn.setCellValueFactory(new MapValueFactory<>("rank"));
        TableColumn<Map, String> avatarColumn = new TableColumn<>("avatar");
        avatarColumn.setCellValueFactory(new MapValueFactory<>("avatar"));
        TableColumn<Map, String> firstNameColumn = new TableColumn<>("username");
        firstNameColumn.setCellValueFactory(new MapValueFactory<>("username"));
        TableColumn<Map, Integer> secondNameColumn = new TableColumn<>("score");
        secondNameColumn.setCellValueFactory(new MapValueFactory<>("score"));
        TableColumn<Map, Integer> column2 = new TableColumn<>("second played");
        column2.setCellValueFactory(new MapValueFactory<>("timePlayed"));
        TableColumn<Map, Integer> column3 = new TableColumn<>("hard played");
        column3.setCellValueFactory(new MapValueFactory<>("hard"));
        TableColumn<Map, Integer> column4 = new TableColumn<>("normal played");
        column4.setCellValueFactory(new MapValueFactory<>("normal"));
        TableColumn<Map, Integer> column5 = new TableColumn<>("easy played");
        column5.setCellValueFactory(new MapValueFactory<>("easy"));
        tableView.getColumns().addAll(rankColumn, avatarColumn,
                firstNameColumn, secondNameColumn, column2,
                column3, column4, column5);
        return tableView;
    }
    private HashMap hashMapDetail(int i) {
        HashMap<String, Object> item1 = new HashMap<>();

        item1.put("rank", getDetailLabel("#"+(i+1), i));
        item1.put("avatar", new ImageView(new Image(users.get(i).getAvatarUrl(), 80, 60, false, false)));
        item1.put("username", getDetailLabel(users.get(i).getUsername(), i));
        item1.put("score", getDetailLabel(users.get(i).getScore()+"", i));
        item1.put("timePlayed", getDetailLabel( users.get(i).getPlayedTimeSecond()+"", i));
        item1.put("hard", getDetailLabel(users.get(i).getHardPlayed()+"", i));
        item1.put("normal", getDetailLabel(users.get(i).getNormalPlayed()+"", i));
        item1.put("easy", getDetailLabel(users.get(i).getEasyPlayed()+"", i));
        return item1;
    }
    private Label getDetailLabel(String detail, int i){
        Label label = new Label(detail);
        if (i==0) label.setStyle("-fx-background-color: gold ; -fx-font-size: 20; -fx-text-fill: black");
        else if (i == 1) label.setStyle("-fx-background-color: silver; -fx-font-size: 20; -fx-text-fill: black");
        else if(i==2) label.setStyle("-fx-background-color: saddlebrown; -fx-font-size: 20; -fx-text-fill: white");
        else label.setStyle("-fx-font-size: 20; -fx-text-fill: white");
        return label;
    }
}
