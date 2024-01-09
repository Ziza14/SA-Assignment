import java.util.ArrayList;
import java.util.List;

public class TennisPlayerManager {
    private List<TennisPlayer> players;

    public TennisPlayerManager() {
        this.players = new ArrayList<>();
    }

    public void registerPlayer(String name, String email, String dateOfBirth, String category) {
        int playerID = generateRandomPlayerID();
        TennisPlayer newPlayer = new TennisPlayer(name, email, dateOfBirth, category, playerID);
        players.add(newPlayer);
        CSVHandler.writePlayerToCSV(newPlayer); // Update to write to the same CSV file
    }

    private int generateRandomPlayerID() {
        return 100 + (int) (Math.random() * 900); // Generates a random 3-digit player ID
    }

    public List<TennisPlayer> getPlayers() {
        return players;
    }

    public TennisPlayer findPlayerById(int playerID) {
        for (TennisPlayer player : players) {
            if (player.getPlayerID() == playerID) {
                return player;
            }
        }
        return null; 
    }
}
