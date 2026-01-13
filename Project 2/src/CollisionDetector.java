import javax.swing.*;
import java.awt.*;

public class CollisionDetector { //I used ai to help me figure out how to use some of the collision, but mostly just the concept
    /**calculates the center, scales, makes a hitbox, returns hitbox collisions
     * @param object1
     * @param object2
     * @param hitboxScale
     * @return
     */
    public static boolean isColliding(JLabel object1, JLabel object2, double hitboxScale) {
        // Get the centers of both objects
        Point center1 = new Point(
            object1.getX() + object1.getWidth() / 2,
            object1.getY() + object1.getHeight() / 2
        );
        Point center2 = new Point(
            object2.getX() + object2.getWidth() / 2,
            object2.getY() + object2.getHeight() / 2
        );

        // Calculate scaled dimensions
        int scaledWidth1 = (int)(object1.getWidth() * hitboxScale);
        int scaledHeight1 = (int)(object1.getHeight() * hitboxScale);
        int scaledWidth2 = (int)(object2.getWidth() * hitboxScale);
        int scaledHeight2 = (int)(object2.getHeight() * hitboxScale);

        // Create scaled rectangles centered on the objects
        Rectangle hitbox1 = new Rectangle(
            center1.x - scaledWidth1 / 2,
            center1.y - scaledHeight1 / 2,
            scaledWidth1,
            scaledHeight1
        );
        Rectangle hitbox2 = new Rectangle(
            center2.x - scaledWidth2 / 2,
            center2.y - scaledHeight2 / 2,
            scaledWidth2,
            scaledHeight2
        );

        return hitbox1.intersects(hitbox2); //returns scaled hitbox collision
    }
}
