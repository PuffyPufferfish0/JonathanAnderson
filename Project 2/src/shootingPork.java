import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class shootingPork extends JLabel implements ActionListener {
    private Timer timer; // Timer for updating projectile position
    private int speed; // Speed of the projectile
    private boolean active; // bool to indicate if the projectile is active
    private int dirX; // xdirection of the projectile
    private int dirY; // Y direction of the projectile
    private List<JLabel> monsters; // List of monsters to check for collision
    private ImageIcon dust; // Icon for dust effect on collision
    private boolean showHitbox; // Flag to indicate if hitbox should be shown

    /**
     * Constructor for shootingPork.
     * @param speed Speed of the projectile
     * @param scale Scale factor for the projectile image
     * @param dustScale Scale factor for the dust image
     * @param monsters List of monsters to check for collision
     */
    public shootingPork(int speed, int scale, int dustScale, List<JLabel> monsters) {
        // Load and scale the projectile image
        ImageIcon originalIcon = new ImageIcon("ham.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(
            originalIcon.getIconWidth() * scale / 100,
            originalIcon.getIconHeight() * scale / 100,
            Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

        this.speed = speed; // Initialize speed
        this.active = false; // Initialize active flag
        this.dirX = 0; // Initialize X direction
        this.dirY = 0; // Initialize Y direction
        this.monsters = monsters; // Initialize list of monsters
        this.showHitbox = false; // Initialize hitbox visibility flag

        // Load and scale the dust image
        ImageIcon originalDustIcon = new ImageIcon("dust.png");
        Image scaledDustImage = originalDustIcon.getImage().getScaledInstance(
            originalDustIcon.getIconWidth() * dustScale / 100,
            originalDustIcon.getIconHeight() * dustScale / 100,
            Image.SCALE_SMOOTH);
        this.dust = new ImageIcon(scaledDustImage);

        setSize(scaledImage.getWidth(null), scaledImage.getHeight(null)); // Set size of the projectile
        setLocation(-50, -50); // Set initial location off-screen

        timer = new Timer(10, this); // Initialize timer with 10ms delay
        timer.start(); // Start the timer
    }

    /**
     * Method to shoot the projectile.
     * @param startX Starting X position
     * @param startY Starting Y position
     * @param dirX X direction
     * @param dirY Y direction
     */
    public void shoot(int startX, int startY, int dirX, int dirY) {
        if (!active) { // Check if projectile is not active
            setLocation(startX, startY); // Set starting location
            this.dirX = dirX; // Set X direction
            this.dirY = dirY; // Set Y direction
            this.active = true; // Set active flah to true
        }
    }

    /**
     * Method to toggle hitbox visibility.
     */
    public void HHitbox() {
        showHitbox = !showHitbox; // Toggle hitbox visibility
        repaint(); // Repaint the component
    }

    /**
     * Method called by the timer to update projectile position.
     * @param e ActionEvent triggered by the timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (active) { // Check if projectile is active
            int x = getX() + dirX * speed; // Calculate new X position
            int y = getY() + dirY * speed; // Calculate new Y position

            // Check if projectile is out of bounds
            if (x < 0 || x > getParent().getWidth() || y < 0 || y > getParent().getHeight()) {
                active = false; // Deactivate projectile
                setLocation(-50, -50); // Move off-screen
            } else {
                setLocation(x, y); // Update location
                checkCollision(); // Check for collision
            }
        }
    }

    /**
     * Method to check for collision with monsters.
     */
    private void checkCollision() {
        Rectangle projectileBounds = getBounds(); // Get bounds of the projectile
        for (JLabel monster : monsters) { // Iterate over monsters
            if (projectileBounds.intersects(monster.getBounds())) { // Check for intersection
                showDust(monster.getLocation()); // Show dust effect
                monster.setVisible(false); // Hide monster
                monsters.remove(monster); // Remove monster from list
                active = false; // Deactivate projectile
                setLocation(-50, -50); // Move off-screen
                break; // Exit loop after collision
            }
        }
    }

    /**
     * Method to show dust effect at a given location.
     * @param location 
     */
    private void showDust(Point location) {
        JLabel dustLabel = new JLabel(dust); // Create label for dust effect
        dustLabel.setSize(dust.getIconWidth(), dust.getIconHeight()); // Set size of dust label
        dustLabel.setLocation(location); // Set location of dust label
        getParent().add(dustLabel); // Add dust label to parent
        getParent().repaint(); // Repaint parent

        // Timer to remove dust effect after 400ms
        Timer dustTimer = new Timer(400, e -> {
            dustLabel.setVisible(false); // Hide dust label
            getParent().remove(dustLabel); // Remove dust label from parent
            getParent().repaint(); // Repaint parent
        });
        dustTimer.setRepeats(false); // Set timer to not repeat
        dustTimer.start(); // Start dust timer
    }

    /**
     * Override paintComponent to draw hitbox if enabled.
     * @param g Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass paintComponent
        if (showHitbox) { // Check if hitbox should be shown
            Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
            g2d.setColor(Color.RED); // Set color for hitbox
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Draw hitbox rectangle
        }
    }
}