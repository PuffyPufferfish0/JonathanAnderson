
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class GameFrame extends JFrame { // GameFrame class extending JFrame for GUI window
    private JPanel background; // Panel for the main menu background
    private CardLayout cardLayout; // CardLayout for switching between different panels
    private JPanel panel; // Main panel to hold different screens
    private JLabel highScoresLabel; // Label to display high scores
    private HighScoreManager highScoreManager; // Instance of HighScoreManager for managing high scores
    private GraphicsDevice puter; // Graphics device for full-screen mode
    private static final int size = 500; // Size for decorative images
    private String difficulty = "Easy"; // Default difficulty level

    public GameFrame() { // Constructor for GameFrame
        highScoreManager = new HighScoreManager(); // Initialize high score manager
        setTitle("PorkingIt"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation

        // Set up full screen
        GraphicsEnvironment Graphics = GraphicsEnvironment.getLocalGraphicsEnvironment(); // Get graphics environment
        puter = Graphics.getDefaultScreenDevice(); // Get default screen device
        setUndecorated(true); // Remove window decorations
        if (puter.isFullScreenSupported()) { // Check if full screen is supported
            puter.setFullScreenWindow(this); // Set window to full screen
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window if full screen not supported
        }

        cardLayout = new CardLayout(); // Initialize CardLayout
        panel = new JPanel(cardLayout); // Initialize main panel with CardLayout
        setContentPane(panel); // Set main panel as content pane

        createMainMenu(); // Create main menu
        panel.add(background, "MENU"); // Add main menu to panel with identifier "MENU"
        
        // Add decorative images
        addDecorativeImages(); // Add decorative images to the main menu

        setVisible(true); // Make window visible
    }

    private void addDecorativeImages() { // Method to add decorative images
        // Create monster decoration (left side)
        JLabel monsterDecoration = GameSpriteUtils.createScaledSprite("monster.png", size); // Create scaled monster sprite
        monsterDecoration.setBounds(50, getHeight()/2 - size/2, size, size); // Set position and size
        background.add(monsterDecoration); // Add monster decoration to background

        // Create pig decoration (right side)
        JLabel pigDecoration = GameSpriteUtils.createScaledSprite("pig.png", size); // Create scaled pig sprite
        pigDecoration.setBounds(getWidth() - size - 50, getHeight() - size - 50, size, size); // Set position and size
        background.add(pigDecoration); // Add pig decoration to background
    }

    private void createMainMenu() { // Method to create the main menu
        background = new JPanel(null); // Initialize background panel with null layout for absolute positioning
        background.setBackground(new Color(50, 50, 50)); // Set background color

        // Create title label
        JLabel titleLabel = new JLabel("Porking It", SwingConstants.CENTER); // Initialize title label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80)); // Set font for title label
        titleLabel.setForeground(Color.WHITE); // Set font color for title label
        titleLabel.setBounds(0, 50, getWidth(), 100); // Set position and size
        background.add(titleLabel); // Add title label to background

        // Create instructions I had the same problems with this as I did with highscore manager, but Im kinda learning this html orientating method of organization
        JLabel instructions = new JLabel("<html><center>Use your mouse to dodge the monsters<br>" +
            "survive for as long as you can<br> Use wasd to shoot your pork<br>Good Luck!</center></html>", 
            SwingConstants.CENTER); // Initialize instructions label
        instructions.setFont(new Font("Comic Sans MS", Font.BOLD, 30)); // Set font for instructions
        instructions.setForeground(Color.WHITE); // Set font color for instructions
        instructions.setBounds(0, 200, getWidth(), 200); // Set position and size
        background.add(instructions); // Add instructions to background

        // Create and position buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Initialize button panel with FlowLayout
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setBounds(0, getHeight()/2 + 100, getWidth(), 50); // Set position and size

        JButton playButton = createMenuButton("Play"); // Create play button
        JButton exitButton = createMenuButton("Exit"); // Create exit button
        
        playButton.addActionListener(e -> { // Add action listener to play button
            dispose(); // Close current window
            SwingUtilities.invokeLater(() -> { // Create new game instance
                Game game = new Game(); // Initialize game
                game.setDifficulty(difficulty); // Set game difficulty
            });
        });
        
        exitButton.addActionListener(e -> System.exit(0)); // Add action listener to exit button

        buttonPanel.add(playButton); // Add play button to button panel
        buttonPanel.add(exitButton); // Add exit button to button panel
        background.add(buttonPanel); // Add button panel to background

        // Position high scores label
        highScoresLabel = new JLabel(highScoreManager.getFormattedHighScores()); // Initialize high scores label
        highScoresLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font for high scores label
        highScoresLabel.setForeground(Color.WHITE); // Set font color for high scores label
        highScoresLabel.setBounds(getWidth() - 400, 50, 350, 300); // Set position and size
        background.add(highScoresLabel); // Add high scores label to background

        // Add reset high scores button
        JButton resetScoresButton = createMenuButton("Reset High Scores"); // Create reset scores button
        resetScoresButton.setBounds(getWidth() - 400, 360, 350, 50); // Set position and size
        resetScoresButton.addActionListener(e -> resetHighScores()); // Add action listener to reset scores button
        background.add(resetScoresButton); // Add reset scores button to background

        // Add difficulty selection buttons
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Initialize difficulty panel with FlowLayout
        difficultyPanel.setOpaque(false); // Make difficulty panel transparent
        difficultyPanel.setBounds(0, getHeight() - 100, getWidth(), 50); // Set position and size

        JToggleButton easyButton = createDifficultyButton("Easy"); // Create easy difficulty button
        JToggleButton mediumButton = createDifficultyButton("Medium"); // Create medium difficulty button
        JToggleButton hardButton = createDifficultyButton("Hard"); // Create hard difficulty button

        ButtonGroup difficultyGroup = new ButtonGroup(); // Initialize button group for difficulty buttons
        difficultyGroup.add(easyButton); // Add easy button to group
        difficultyGroup.add(mediumButton); // Add medium button to group
        difficultyGroup.add(hardButton); // Add hard button to group

        difficultyPanel.add(easyButton); // Add easy button to difficulty panel
        difficultyPanel.add(mediumButton); // Add medium button to difficulty panel
        difficultyPanel.add(hardButton); // Add hard button to difficulty panel
        background.add(difficultyPanel); // Add difficulty panel to background

        // Add action listeners to difficulty buttons
        easyButton.addActionListener(e -> difficulty = "Easy"); // Set difficulty to easy
        mediumButton.addActionListener(e -> difficulty = "Medium"); // Set difficulty to medium
        hardButton.addActionListener(e -> difficulty = "Hard"); // Set difficulty to hard

        // Add component listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { // Override componentResized method
                // Update positions based on new window size
                titleLabel.setBounds(0, 50, getWidth(), 100); // Update title label position
                instructions.setBounds(0, 200, getWidth(), 200); // Update instructions position
                buttonPanel.setBounds(0, getHeight()/2 + 100, getWidth(), 50); // Update button panel position
                highScoresLabel.setBounds(getWidth() - 400, 50, 350, 300); // Update high scores label position
                resetScoresButton.setBounds(getWidth() - 400, 360, 350, 50); // Update reset scores button position
                difficultyPanel.setBounds(0, getHeight() - 100, getWidth(), 50); // Update difficulty panel position
                
                // Update decorative images positions
                for (Component comp : background.getComponents()) { // Iterate over components in background
                    if (comp instanceof JLabel && comp != titleLabel && 
                        comp != instructions && comp != highScoresLabel) { // Check if component is a decorative image
                        if (comp.getX() < getWidth()/2) { // Check if component is on the left side
                            // Monster (left side)
                            comp.setBounds(50, getHeight()/2 - size/2, size, size); // Update monster position
                        } else {
                            // Pig (right side)
                            comp.setBounds(getWidth() - size - 50, getHeight() - size - 50, size, size); // Update pig position
                        }
                    }
                }
            }
        });
    }

    private JButton createMenuButton(String text) { // Method to create a menu button
        JButton button = new JButton(text); // Initialize button with text
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // Set font for button
        button.setPreferredSize(new Dimension(200, 50)); // Set preferred size for button
        button.setBackground(new Color(70, 130, 180)); // Set background color for button
        button.setForeground(Color.WHITE); // Set font color for button
        button.setFocusPainted(false); // Disable focus painting
        button.setBorderPainted(false); // Disable border painting
        
        button.addMouseListener(new MouseAdapter() { // Add mouse listener for hover effects
            @Override
            public void mouseEntered(MouseEvent evt) { // Override mouseEntered method
                button.setBackground(new Color(100, 149, 237)); // Change background color on hover
            }
            
            @Override
            public void mouseExited(MouseEvent evt) { // Override mouseExited method
                button.setBackground(new Color(70, 130, 180)); // Revert background color on exit
            }
        });
        
        return button; // Return the created button
    }

    private JToggleButton createDifficultyButton(String text) { // Method to create a difficulty button
        JToggleButton button = new JToggleButton(text); // Initialize toggle button with text
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // Set font for button
        button.setPreferredSize(new Dimension(150, 50)); // Set preferred size for button
        button.setBackground(new Color(70, 130, 180)); // Set background color for button
        button.setForeground(Color.WHITE); // Set font color for button
        button.setFocusPainted(false); // Disable focus painting
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Set border for button

        button.addItemListener(e -> { // Add item listener for selection 
            if (e.getStateChange() == ItemEvent.SELECTED) { // Check if button is selected
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3)); // Change border color on selection (oracle)
            } else {
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Revert border color on deselection
            }
        });

        return button; // Return the toggle button
    }

    private void resetHighScores() { // Method to reset high scores
        try {
            List<String> lines = Files.readAllLines(Paths.get("default.txt")); // Read high scores from file
            highScoreManager.resetScores(lines); // Reset high scores using high score manager
            highScoresLabel.setText(highScoreManager.getFormattedHighScores()); // Update high scores label
        } catch (IOException e) { // Catch IOException
            JOptionPane.showMessageDialog(this, "Error reading default high scores.", // Show error message
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Method to show the main menu
     * 
     */
    public void showMenu() { 
        cardLayout.show(panel, "MENU"); // Show the menu panel using CardLayout
    }

    /**main method
     * @param args
     */
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(GameFrame::new); // Create new GameFrame instance on event dispatch thread
    }
}