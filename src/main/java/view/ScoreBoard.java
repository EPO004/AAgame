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
        primaryStage.setScene(new Scene(pane));
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
        item1.put("rank", "#" + (i + 1));
        item1.put("avatar", new ImageView(new Image(users.get(i).getAvatarUrl(), 80, 60, false, false)));
        item1.put("username", users.get(i).getUsername());
        item1.put("score", users.get(i).getScore());
        item1.put("timePlayed", users.get(i).getPlayedTimeSecond());
        item1.put("hard", users.get(i).getHardPlayed());
        item1.put("normal", users.get(i).getNormalPlayed());
        item1.put("easy", users.get(i).getEasyPlayed());
        return item1;
    }
}
