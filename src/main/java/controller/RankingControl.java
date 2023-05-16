package controller;

import javafx.scene.input.MouseEvent;
import model.User;
import view.LoginMenu;
import view.MainMenu;
import view.ScoreBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankingControl {
    public static ArrayList byScore(){
        ArrayList<User> users = User.getUsers();
        Collections.sort(users, new Comparator<User>() {
            public int compare(User a, User b) {
                return b.getScore() - a.getScore();
            }
        });
        Collections.sort(users, new Comparator<User>() {
            public int compare(User a, User b) {
                if (a.getScore() == b.getScore())
                    return b.getPlayedTimeSecond() - a.getPlayedTimeSecond();
                return 0;
            }
        });
        return users;
    }
    public static ArrayList byDifficulty(){
        ArrayList<User> users = User.getUsers();
        Collections.sort(users, new Comparator<User>() {
            public int compare(User a, User b) {
                return b.getHardPlayed() - a.getHardPlayed();
            }
        });
        Collections.sort(users, new Comparator<User>() {
            public int compare(User a, User b) {
                if (a.getHardPlayed() == b.getHardPlayed())
                    return b.getNormalPlayed()- a.getNormalPlayed();
                return 0;
            }
        });
        Collections.sort(users, new Comparator<User>() {
            public int compare(User a, User b) {
                if (a.getHardPlayed() == b.getHardPlayed() && b.getNormalPlayed()==a.getNormalPlayed())
                    return b.getEasyPlayed()- a.getEasyPlayed();
                return 0;
            }
        });
        return users;
    }

    public void byScore(MouseEvent mouseEvent) throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard(byScore());
        scoreBoard.start(LoginMenu.getStage());
    }

    public void byDifficulty(MouseEvent mouseEvent) throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard(byDifficulty());
        scoreBoard.start(LoginMenu.getStage());
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MainMenu mainMenu = new MainMenu(MainMenu.onMusic, MainMenu.getUser());
        mainMenu.start(LoginMenu.getStage());
    }
}
