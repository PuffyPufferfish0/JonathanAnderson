import javax.swing.*;

public class run {
    /**litterally just a class that uses SwingUtils to run GameFrame, which can run Game
     * @param args
     */
    public static void main(String[] args) {
            SwingUtilities.invokeLater(GameFrame::new);//runs GameFrame which then can run Game

    }
}


///I lowkey wish I just made pong or something this took me weeks and for what kms