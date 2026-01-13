import javax.swing.*;
import java.awt.*;

public class GameSpriteUtils { 
    private static final int size = 100; // Default size for sprites

    /**
     * Creates a JLabel with a properly scaled sprite image.
     * @param imagePath Path to the image file
     * @param targetSize size (keeps aspect ratio)
     * @return JLabel containing the scaled sprite
     */
    public static JLabel createScaledSprite(String imagePath, int targetSize) {
        ImageIcon originalIcon = new ImageIcon(imagePath); // Load the image as an icon
        Image originalImage = originalIcon.getImage(); // Extract the image from the icon
        
        // Calculate scaling factor while maintaining aspect ratio
        double scale = Math.min(
            (double) targetSize / originalImage.getWidth(null), (double) targetSize / originalImage.getHeight(null));
        
        int scaledWidth = (int) (originalImage.getWidth(null) * scale); // Calculate scaled width
        int scaledHeight = (int) (originalImage.getHeight(null) * scale); // Calculate scaled height
        
        // Scale the image smoothly to the calculated dimensions
        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage)); // Create a JLabel with the scaled image
        iconLabel.setSize(scaledWidth, scaledHeight); // Set the size of the label to match the image
        
        return iconLabel; // Return the JLabel containing the scaled sprite
    }

    /**
     * Creates a JLabel with a properly scaled sprite image using default size.
     * @param imagePath Psth to the image file
     * @return JLabel containing the scaled sprite
     */
    public static JLabel createScaledSprite(String imagePath) {
        return createScaledSprite(imagePath, size); // Use the default size for scaling
    }

    /**
     * Gets the scaled dimensions for an image while maintaining aspect ratio.
     * @param imagePath 'Path to the image file
     * @param targetSize goal size
     * @return Dimension object containing scaled width and height
     */
    public static Dimension getScaledDimensions(String imagePath, int targetSize) {
        ImageIcon originalIcon = new ImageIcon(imagePath); // Load the image as an icon
        Image originalImage = originalIcon.getImage(); // Extract the image from the icon
        
        // Calculate scaling factor while mainaining aspect ratio
        double scale = Math.min((double) targetSize / originalImage.getWidth(null), (double) targetSize / originalImage.getHeight(null));
        
        int scaledWidth = (int) (originalImage.getWidth(null) * scale); // Calculate scaled width
        int scaledHeight = (int) (originalImage.getHeight(null) * scale); // Calculate scaled height
        
        return new Dimension(scaledWidth, scaledHeight); // Return the dimensions of the scaled image
    }
}