package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String avatarUrl;
    private int score;
    private int playedTimeSecond;
    private int hardPlayed;
    private int normalPlayed;
    private int easyPlayed;
    private GameSetting gameSetting;
    private static ArrayList<User> users = new ArrayList<>();
    private static HashMap<String, User> userInfo = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        gameSetting = new GameSetting(2, 10, 1.5, 5, 5, 5, 1, true, true, 0);
        users.add(this);
        userInfo.put(username, this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static HashMap<String, User> getUserInfo() {
        return userInfo;
    }
    public boolean passwordMatch(String password){
        if (this.password.equals(password)) return true;
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public void removeUser(User user){
        userInfo.remove(user.getUsername(), user);
        users.remove(user);
    }

    public int getPlayedTimeSecond() {
        return playedTimeSecond;
    }

    public void setPlayedTimeSecond(int playedTimeSecond) {
        this.playedTimeSecond = playedTimeSecond;
    }

    public int getHardPlayed() {
        return hardPlayed;
    }

    public void setHardPlayed(int hardPlayed) {
        this.hardPlayed = hardPlayed;
    }

    public int getNormalPlayed() {
        return normalPlayed;
    }

    public void setNormalPlayed(int normalPlayed) {
        this.normalPlayed = normalPlayed;
    }

    public int getEasyPlayed() {
        return easyPlayed;
    }

    public void setEasyPlayed(int easyPlayed) {
        this.easyPlayed = easyPlayed;
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }
}
