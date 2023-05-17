package model;

public class GameSetting {
    private int difficulty;
    private int turningVelocity;
    private double windVelocity;
    private int freezeSecond;
    private int mapBallFormat;
    private int allBalls;
    private int mapFormat;
    private boolean musicOn;
    private boolean soundOn;
    private int isBlackWhite;

    public GameSetting(int difficulty, int turningVelocity, double windVelocity,
                       int freezeSecond, int mapBallFormat, int allBalls,
                       int mapFormat, boolean musicOn, boolean soundOn,
                       int isBlackWhite) {
        this.difficulty = difficulty;
        this.turningVelocity = turningVelocity;
        this.windVelocity = windVelocity;
        this.freezeSecond = freezeSecond;
        this.mapBallFormat = mapBallFormat;
        this.allBalls = allBalls;
        this.mapFormat = mapFormat;
        this.musicOn = musicOn;
        this.soundOn = soundOn;
        this.isBlackWhite = isBlackWhite;
    }

    public double getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(double windVelocity) {
        this.windVelocity = windVelocity;
    }

    public int getFreezeSecond() {
        return freezeSecond;
    }

    public void setFreezeSecond(int freezeSecond) {
        this.freezeSecond = freezeSecond;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTurningVelocity() {
        return turningVelocity;
    }

    public void setTurningVelocity(int turningVelocity) {
        this.turningVelocity = turningVelocity;
    }

    public int getMapBallFormat() {
        return mapBallFormat;
    }

    public void setMapBallFormat(int mapBallFormat) {
        this.mapBallFormat = mapBallFormat;
    }

    public int getAllBalls() {
        return allBalls;
    }

    public void setAllBalls(int allBalls) {
        this.allBalls = allBalls;
    }

    public int getMapFormat() {
        return mapFormat;
    }

    public void setMapFormat(int mapFormat) {
        this.mapFormat = mapFormat;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }

    public int getIsBlackWhite() {
        return isBlackWhite;
    }

    public void setIsBlackWhite(int isBlackWhite) {
        this.isBlackWhite = isBlackWhite;
    }
    public void changeDifficulty(int amount){
        this.difficulty += amount;
    }
    public void changeBalls(int amount){
        this.allBalls += amount;
    }
    public void changeFormat(int amount){
        this.mapFormat += amount;
    }
    public void changeMapBall(int amount){
        this.mapBallFormat+=amount;
    }
}

/*
*ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1);
 */
