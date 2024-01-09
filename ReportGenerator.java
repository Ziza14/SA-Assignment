import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    private List<Score> scores;

    public ReportGenerator() {
        this.scores = readScoresFromCSV();
    }

    public String generateAverageScoreReport(int playerID) {
        double averageScore = calculateAverageScore(playerID);
        int playerAge = getPlayerAge(playerID); 
        return "Average Score Report for Player " + playerID + " (Age: " + playerAge + "): " + averageScore;
    }

    public String generateFullReport(int playerID) {
        StringBuilder fullReport = new StringBuilder("Full Report for Player " + playerID + ":\n");
        int playerAge = getPlayerAge(playerID); 
        fullReport.append("Player Age: ").append(playerAge).append("\n");

        for (Score score : scores) {
            if (score.getPlayerID() == playerID) {
                fullReport.append("Set01: ").append(score.getSet01Scores()).append("\n");
                fullReport.append("Set02: ").append(score.getSet02Scores()).append("\n");
                fullReport.append("Set03: ").append(score.getSet03Scores()).append("\n");
                fullReport.append("Win: ").append(score.getTotalScores()).append("\n");
            }
        }
        return fullReport.toString();
    }

    private List<Score> readScoresFromCSV() {
        List<Score> scoresList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("tournament_data.csv"))) {
            String line;
            
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int playerID = Integer.parseInt(data[0]);
                int set01 = Integer.parseInt(data[5]);  
                int set02 = Integer.parseInt(data[6]); 
                int set03 = Integer.parseInt(data[7]); 
                int win = Integer.parseInt(data[8]); 
                scoresList.add(new Score(playerID, set01, set02, set03, win));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scoresList;
    }

    private double calculateAverageScore(int playerID) {
        int totalScore = 0;
        int count = 0;
        for (Score score : scores) {
            if (score.getPlayerID() == playerID) {
                totalScore += score.getSet01Scores() + score.getSet02Scores() + score.getSet03Scores() + score.getTotalScores();
                count += 4; 
            }
        }
        return count > 0 ? (double) totalScore / count : 0;
    }

    private int getPlayerAge(int playerID) {
        TennisPlayer player = CSVHandler.getPlayerById(playerID);
        if (player != null) {
            return player.calculateAge();
        } else {
            System.out.println("Player not found with ID: " + playerID);
            return -1; 
        }
    }
}
