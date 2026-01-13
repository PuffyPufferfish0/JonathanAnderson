import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class HighScoreManager {
    private static final String score = "highscores.txt"; // File name for storing high scores
    private static final int maxScoresNum = 5; // Maximum number of high scores to keep
    private ArrayList<Double> highScores; // List to store high scores
    private DecimalFormat decimalFormat; // Formatter for displaying scores with three decimal places
    private File scoreFile; // File object representing the high score file

    /**
     * Constructor initializes the high score manager.
     * It sets up the list, formatter, and file, then loads existing scores.
     */
    public HighScoreManager() {
        highScores = new ArrayList<>(); // Initialize the list for high scores
        decimalFormat = new DecimalFormat("000.000"); // Set format for scores to three decimal places
        scoreFile = new File(score); // Create a File object for the high score file
        try {
            scoreFile.createNewFile(); // Create the file if it doesn't exist
        } catch (IOException e) {
            System.err.println(" " + e.getMessage()); // Print error message if file creation fails
        }
        loadHighScores(); // Load existing high scores from the file
    }

    /**
     * Adds a new score to the list and maintains the top scores.
     * @param score The score to add
     */
    public void addScore(double score) {
        highScores.add(score); // Add the new score to the list
        Collections.sort(highScores, Collections.reverseOrder()); // Sort scores in descending order
        if (highScores.size() > maxScoresNum) { // If there are more scores than allowed
            highScores.remove(maxScoresNum); // Remove the lowest score
        }
        saveHighScores(); // Save the updated list of scores to the file
    }

    /**
     * Returns a copy of the high scores list.
     * @return A new list containing the high scores
     */
    public ArrayList<Double> getHighScores() {
        return new ArrayList<>(highScores); // Return a copy of the high scores list
    }

    /**
     * Returns a formatted string of high scores for display. I used ai on this whole method basically
     * I could make it show up, but it would just list them all out no matter what I changed 
     * @return A string formatted as HTML for displaying high scores 
     */
    public String getFormattedHighScores() {
        StringBuilder sb = new StringBuilder("<html><body style='color: white'>"); // Start HTML string
        sb.append("<h3>High Scores:</h3>"); // Add header
        if (highScores.isEmpty()) { // Check if there are no scores (this is basically useless now but I wanted to see if it woudl show up when I was making it)
            sb.append("No."); // Display message if no scores(it never will)
        } else {
            for (int i = 0; i < highScores.size(); i++) { // Iterate over scores
                sb.append(String.format("%d. %s<br>", 
                    i + 1, decimalFormat.format(highScores.get(i)))); // Format each score
            }
        }
        sb.append("</body></html>"); // Close HTML string
        return sb.toString(); // Return the formatted string
    }

    /**
     * Loads high scores from the file into the list.
     */
    private void loadHighScores() {
        if (!scoreFile.exists() || !scoreFile.canRead()) { // Check if file exists and is readable
            System.err.println(" "); // Print error if file is not accessible
            return; // Exit method if file is not accessible
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) { // Open file for reading
            String line;
            while ((line = reader.readLine()) != null) { // Read each line from the file
                try {
                    double score = Double.parseDouble(line.trim()); // splits score from line
                    highScores.add(score); // Add score to list
                } catch (NumberFormatException e) {
                    System.err.println(" " + line); // Print error if line is not a valid number
                }
            }
            Collections.sort(highScores, Collections.reverseOrder()); // Sort scores in descending order
            while (highScores.size() > maxScoresNum) { // Ensure list size does not exceed max
                highScores.remove(maxScoresNum); // Remove lowest scores if necessary
            }
        } catch (IOException e) {
            System.err.println("" + e.getMessage()); // print error if file reading fail
        }
    }

    /**
     * Saves the current list of high scores to the file.
     */
    private void saveHighScores() {
        if (!scoreFile.canWrite()) { // Check if file is writable
            System.err.println(""); // Print error if is not writable
            return; // Exit method if file is not writable
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(scoreFile))) { // Open file for writing
            for (Double score : highScores) { // Iterate over scores for each score
                writer.println(score); // Write each score to the file
            }
            writer.flush(); // Ensure all data is written to the file (a random comment on reddit said to always do this)
        } catch (IOException e) {
            System.err.println(" " + e.getMessage()); // Print error if file writing fails
        }
    }

    /**
     * Checks if a given score qualifies as a high score.
     * @param score The score to check
     * @return true if the score is a high score, false otherwise
     */
    public boolean isHighScore(double score) {
        if (highScores.size() < maxScoresNum) return true; // If fewer scores than max, it's a high score
        return !highScores.isEmpty() && score > highScores.get(highScores.size() - 1); // Check if score is higher than the lowest high score
    }

    /**
     * Resets the high scores to a default list.
     * @param defaultScores List of default scores to reset to
     */
    public void resetScores(List<String> defaultScores) {
        highScores.clear(); // Clear current high scores
        for (String scoreStr : defaultScores) { // Iterate over default scores
            try {
                double score = Double.parseDouble(scoreStr.trim()); // Parse score from string
                highScores.add(score); // Add score to list
            } catch (NumberFormatException e) {
                System.err.println(" " + scoreStr); // Prints error if string is not a valid number
            }
        }
        Collections.sort(highScores, Collections.reverseOrder()); // Sort scores in descending order
        while (highScores.size() > maxScoresNum) { // Ensure list size does not exceed max
            highScores.remove(maxScoresNum); // Remove lowest scores if necessary
        }
        saveHighScores(); // Save the updated list of scores to the file
    }
}