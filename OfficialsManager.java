import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OfficialsManager {
    private List<Officials> moderators;

    public OfficialsManager() {
        this.moderators = new ArrayList<>();
        // Add a sample moderator
        moderators.add(new Officials("admin", "root"));
    }

    public boolean login(String username, String password) {
        for (Officials moderator : moderators) {
            if (moderator.login(username, password)) {
                return true; // Login successful
            }
        }
        return false; // Login failed
    }

    public void inputScores(int playerID, int attack, int defense, int boosting, int blocking) {
        Score newScore = new Score(playerID, attack, defense, boosting, blocking);
        saveScoreToCSV(newScore);
    }

    private void saveScoreToCSV(Score score) {
        // Use CSVHandler to update scores along with player details in the same CSV file
        TennisPlayer player = CSVHandler.getPlayerById(score.getPlayerID());

        if (player != null) {
            player.setScores(score);
            CSVHandler.updatePlayerScores(player);
        } else {
            System.out.println("Player not found with ID: " + score.getPlayerID());
        }
    }
}
