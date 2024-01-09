public class Score {
    private int playerID;
    private int set01;
    private int set02;
    private int set03;
    private int win;

    public Score(int playerID, int Set01, int Set02, int Set03, int Win) {
        this.playerID = playerID;
        this.set01 = Set01;
        this.set02 = Set02;
        this.set03 = Set03;
        this.win = Win;
    }

    // Getter methods
    public int getPlayerID() {
        return playerID;
    }

    public int getSet01Scores() {
        return set01;
    }

    public int getSet02Scores() {
        return set02;
    }

    public int getSet03Scores() {
        return set03;
    }

    public int getTotalScores() {
        return win;
    }


    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setSet01Scores(int set01) {
        this.set01 = set01;
    }

    public void setSet02Scores(int set02) {
        this.set02 = set02;
    }

    public void setSet03Scores(int set03) {
        this.set03 = set03;
    }

    public void setTotalScores(int win) {
        this.win = win;
    }
}
