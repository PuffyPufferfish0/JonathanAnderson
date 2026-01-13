import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.lang.reflect.Method; 
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List; 
import java.util.Map; 

public class Game extends JFrame { // Game class extending JFrame for GUI window
    //I used extends JFrame, because I like to be able to access the JFrame easily in other classes, I asked milligan and she said it counted as making a Frame
    private JLabel pigLabel;//what the pig png lives in
    private JLabel timerLabel;//where the timer lived
    private Timer gameTimer;//using Timer built into Swing
    private Timer spawnTimer;//using a second timer to run customize monster spawnrate
    private double timeElapsed;//every 1000 
    private List<JLabel> monsters; // monsters arrayList for updating position and speed of update(frameRate and speed)
    private int spawnCount = 1; //spawnCount is how many mosnters are on the screen
    private Map<JLabel, Double> monsterSpeeds;//I used a hashmap are you proud of me? this is used to adjust the speed the monsters update, in pair with the monsters 
    private HighScoreManager highScoreManager; //see HighScoreManager Class, this is an instance of that
    private static final int frames = 256;//this is the frame rate, or the tick rate. I used swings timer to change this, I found that the game runs smoothly at 256
    private long lastUpdateTime; // Variable to track the last time the game was updated
    private static final int size = 200; // Size for sprites
    private boolean showHitboxes = false; // Flag to toggle hitbox visibility
    private static final double widthScal = 0.6;  // 60% of sprite width
    private static final double hiegtScale = 0.4; // 40% of sprite height 
    private shootingPork porkjectile; // Instance of shootingPork for projectiles
    private int spawnInterval = 2500; // Default spawn interval for monsters

    public Game() { 
        //setup for the game window
        highScoreManager = new HighScoreManager(); //Initialize highscoremanager
        setTitle("Porking  It");//window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set to exit on close

        // Set up window with full screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//get graphics environment
        GraphicsDevice gd = ge.getDefaultScreenDevice(); //get the screen
        if (gd.isFullScreenSupported()) { //if fullscreen is supported
            setUndecorated(true);//makes the window borderless kinda makes it fullscreen
            gd.setFullScreenWindow(this); //make window fullscreen
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);//maximize window
            setUndecorated(true);//fullscreen
        }

        // Set dark background
        Container contentPane = getContentPane();// Get content pane
        contentPane.setBackground(new Color(50, 50, 50));// Set background color to black grey

        lastUpdateTime = System.currentTimeMillis(); //the last update time
        initializeGameComponents(); //gamepieces
        setupInputHandlers(); //input handling
        setupGameTimers(); //timers for timing, not the time timer
        
        setVisible(true); //make window visible
    }

    /**Method to initialize game components
     * 
     */
    private void initializeGameComponents() {
        monsters = new ArrayList<>();//monster list
        monsterSpeeds = new HashMap<>(); //monster speed map (this took forever)

        // Create scaled pig sprite out of a png (my girlfriend drew the sprites thanks audree)
        pigLabel = GameSpriteUtils.createScaledSprite("pig.png", size);

        // Set up timer timer not timing timer
        timerLabel = new JLabel("Time: 000.000"); //initial time
        timerLabel.setFont(new Font("Arial", Font.BOLD, 30)); //font bold
        timerLabel.setForeground(Color.WHITE); //white font color

        setLayout(null); //I was told by the internet this was better for positioning
        add(pigLabel); //add piggy content pane
        add(timerLabel); //add timer to content pane

        // Initialize porkjectile
        porkjectile = new shootingPork(30, spawnCount, 15, monsters); //makes the shooting pork with dustcasle
        add(porkjectile);

        // Center pig initially
        centerPig();
        updateTimerPosition();

        // Handle window resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTimerPosition();
                centerPig();
            }
        });
    }

    private void centerPig() {
        pigLabel.setLocation(
            (getWidth() - pigLabel.getWidth()) / 2, // Center pig horizontally
            (getHeight() - pigLabel.getHeight()) / 2 // Center pig vertically
        );
    }

    private void setupInputHandlers() {
        // Create transparent cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor transparentCursor = toolkit.createCustomCursor(
            toolkit.createImage(new byte[0]), new Point(0, 0), "InvisibleCursor");

        // Add mouse control
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                pigLabel.setLocation(
                    e.getX() - pigLabel.getWidth() / 2, // Center pig on mouse X
                    e.getY() - pigLabel.getHeight() / 2 // Center pig on mouse Y
                );
                setCursor(pigLabel.getBounds().contains(e.getPoint()) ? 
                    transparentCursor : Cursor.getDefaultCursor()); // Change cursor if over pig
                if (showHitboxes) {
                    repaint(); // Repaint if hitboxes are shown
                }
            }
        });

        // Add key handlers
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int startX = pigLabel.getX() + pigLabel.getWidth() / 2; // Calculate start X for projectile
                int startY = pigLabel.getY() + pigLabel.getHeight() / 2; // Calculate start Y for projectile
                int dirX = 0; // Initialize direction X
                int dirY = 0; // Initialize direction Y

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: // Move up
                        dirX = 0;
                        dirY = -1;
                        break;
                    case KeyEvent.VK_A: // Move left
                        dirX = -1;
                        dirY = 0;
                        break;
                    case KeyEvent.VK_S: // Move down
                        dirX = 0;
                        dirY = 1;
                        break;
                    case KeyEvent.VK_D: // Move right
                        dirX = 1;
                        dirY = 0;
                        break;
                }

                porkjectile.shoot(startX, startY, dirX, dirY); // Shoot projectile

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Exit game
                    dispose(); // Close window
                    SwingUtilities.invokeLater(GameFrame::new); // Open new game frame
                } else if (e.getKeyCode() == KeyEvent.VK_H) { // Toggle hitboxes
                    showHitboxes = !showHitboxes;
                    repaint(); // Repaint window
                }
            }
        });
        setFocusable(true); // Make window focusable
        requestFocus(); // Request focus for window
    }

    private void setupGameTimers() {
        gameTimer = new Timer(1000 / frames, e -> updateGame()); // Timer for game updates
        gameTimer.start(); // Start game timer

        spawnTimer = new Timer(spawnInterval, e -> { // Timer for spawning monsters
            for (int i = 0; i < spawnCount; i++) {
                spawnMonster(); // Spawn monsters
            }
            spawnCount++; // Increase spawn count
        });
        spawnTimer.start(); // Start spawn timer

        spawnMonster(); // Initial monster
    }

    private void updateGame() {
        long currentTime = System.currentTimeMillis(); // Get current time
        double deltaTime = (currentTime - lastUpdateTime) / 1000.0; // Calculate time difference
        lastUpdateTime = currentTime; // Update last update time

        timeElapsed += deltaTime; // Increment elapsed time
        timerLabel.setText(String.format("Time: %07.9f", timeElapsed)); // Update timer label

        Rectangle pigHitbox = getHitbox(pigLabel); // Get pig hitbox
        
        for (JLabel monsterLabel : new ArrayList<>(monsters)) { // Iterate over monsters
            updateMonsterPosition(monsterLabel, deltaTime); // Update monster position
            if (pigHitbox.intersects(getHitbox(monsterLabel))) { // Check for collision
                handleCollision(); // Handle collision
                return; // Exit method
            }
        }
        
        if (showHitboxes) {
            repaint(); // Repaint if hitboxes are shown
        }
    }

    private void updateMonsterPosition(JLabel monsterLabel, double changeInTime) {
        Point pigCenter = new Point(
            pigLabel.getX() + pigLabel.getWidth() / 2, // Calculate pig center X
            pigLabel.getY() + pigLabel.getHeight() / 2 // Calculate pig center Y
        );
        Point monsterCenter = new Point(
            monsterLabel.getX() + monsterLabel.getWidth() / 2, // Calculate monster center X
            monsterLabel.getY() + monsterLabel.getHeight() / 2 // Calculate monster center Y
        );

        double dx = pigCenter.x - monsterCenter.x; // Calculate X distance
        double dy = pigCenter.y - monsterCenter.y; // Calculate Y distance
        double distance = Math.sqrt(dx * dx + dy * dy); // Calculate distance

        if (distance > 0) {
            double speed = monsterSpeeds.get(monsterLabel); // Get monster speed
            double moveX = speed * dx / distance * changeInTime * 60; // Calculate X movement
            double moveY = speed * dy / distance * changeInTime * 60; // Calculate Y movement
            monsterLabel.setLocation(
                monsterLabel.getX() + (int)moveX, // Update monster X position
                monsterLabel.getY() + (int)moveY // Update monster Y position
            );
        }
    }

    private void spawnMonster() {
        JLabel monsterLabel = GameSpriteUtils.createScaledSprite("monster.png", size); // Create monster sprite
        double speed = 5 + Math.random() * 10; // Randomize monster speed
        monsterSpeeds.put(monsterLabel, speed); // Store monster speed
        
        // Spawn monster outside the visible area
        int edge = (int)(Math.random() * 4); // Randomize spawn edge
        switch(edge) {
            case 0: // top
                monsterLabel.setLocation((int)(Math.random() * getWidth()), -monsterLabel.getHeight());
                break;
            case 1: // right
                monsterLabel.setLocation(getWidth(), (int)(Math.random() * getHeight()));
                break;
            case 2: // bottom
                monsterLabel.setLocation((int)(Math.random() * getWidth()), getHeight());
                break;
            case 3: // left
                monsterLabel.setLocation(-monsterLabel.getWidth(), (int)(Math.random() * getHeight()));
                break;
        }

        add(monsterLabel); // Add monster to content pane
        monsters.add(monsterLabel); // Add monster to list
    }

    private void handleCollision() {
        gameTimer.stop(); // Stop game timer
        spawnTimer.stop(); // Stop spawn timer
        
        String formattedTime = String.format("You survived for %07.3f seconds!", timeElapsed); // Format elapsed time
        
        if (highScoreManager.isHighScore(timeElapsed)) { // Check if high score
            highScoreManager.addScore(timeElapsed); // Add high score
            formattedTime = "NEW HIGH SCORE!\n" + formattedTime; // Update formatted time
        }

        int choice = JOptionPane.showOptionDialog(this, // Show game over dialog
            formattedTime,
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Try Again", "Menu"},
            "Try Again");

        if (choice == JOptionPane.YES_OPTION) { // If try again selected
            resetGame(); // Reset game
        } else {
            dispose(); // Dispose of window
            SwingUtilities.invokeLater(GameFrame::new); // Create new GameFrame
        }
    }

    private void resetGame() {
        timeElapsed = 0; // Reset elapsed time
        spawnCount = 1; // Reset spawn count
        lastUpdateTime = System.currentTimeMillis(); // Reset last update time
        
        for (JLabel monster : monsters) { // Loop through monsters
            remove(monster); // Remove monster from content pane
        }
        monsters.clear(); // Clear monster list
        monsterSpeeds.clear(); // Clear monster speeds
        
        centerPig(); // Center pig
        
        gameTimer.start(); // Start game timer
        spawnTimer.start(); // Start spawn timer
        
        spawnMonster(); // Spawn initial monster
        
        revalidate(); // Revalidate content pane
        repaint(); // Repaint window
    }

    private void updateTimerPosition() {
        timerLabel.setBounds(
            getWidth() - 200, // Position timer from right
            10, // Position timer from top
            180, // Width of timer label
            40 // Height of timer label
        );
    }

    private Rectangle getHitbox(JLabel sprite) {
        int width = (int)(sprite.getWidth() * widthScal); // Calculate hitbox width
        int height = (int)(sprite.getHeight() * hiegtScale); // Calculate hitbox height
        int x = sprite.getX() + (sprite.getWidth() - width) / 2; // Calculate hitbox X position
        int y = sprite.getY() + (sprite.getHeight() - height) / 2; // Calculate hitbox Y position
        return new Rectangle(x, y, width, height); // Return hitbox rectangle
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Call superclass paint method
        if (showHitboxes) {
            Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
            g2d.setColor(Color.RED); // Set color for hitboxes
            
            // Draw pig hitbox
            Rectangle pigHitbox = getHitbox(pigLabel); // Get pig hitbox
            g2d.drawRect(pigHitbox.x, pigHitbox.y, pigHitbox.width, pigHitbox.height); // Draw pig hitbox
            
            // Draw monster hitboxes
            for (JLabel monster : monsters) { // Loop through monsters
                Rectangle monsterHitbox = getHitbox(monster); // Get monster hitbox
                g2d.drawRect(monsterHitbox.x, monsterHitbox.y, // Draw monster hitbox
                monsterHitbox.width, monsterHitbox.height);
            }
        }
    }

    public void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy": // Easy difficulty
                spawnInterval = 5000; // Set spawn interval
                break;
            case "Medium": // Medium difficulty
                spawnInterval = 4000; // Set spawn interval
                break;
            case "Hard": // Hard difficulty
                spawnInterval = 1000; // Set spawn interval
                break;
        }
        if (spawnTimer != null) { // Check if spawn timer is initialized
            spawnTimer.setDelay(spawnInterval); // Set spawn timer delay
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new); // Create new Game instance on event dispatch thread
    }
}