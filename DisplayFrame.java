import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A utility class for displaying a BufferedImage in a JFrame window.
 * This class ensures that the image is displayed in a reusable frame.
 */
class DisplayFrame {
    // Static JFrame instance to enable frame reuse across multiple images
    private static JFrame frame;

    /**
     * Displays the given BufferedImage in a JFrame. If a frame already exists,
     * the image in the frame is updated and the frame is repainted.
     *
     * @param image the BufferedImage to display
     */
    public static void displayFrame(BufferedImage image) {
        if (frame == null) {
            // Initialize the JFrame on first use
            frame = new JFrame("Image Processing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the frame size to match the image dimensions
            frame.setSize(image.getWidth(), image.getHeight());

            // Create a JLabel with the image and add it to the frame
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);

            // Make the frame visible
            frame.setVisible(true);
        } else {
            // Update the image in the existing frame
            JLabel label = (JLabel) frame.getContentPane().getComponent(0);
            ImageIcon icon = (ImageIcon) label.getIcon();
            icon.setImage(image);

            // Repaint the frame to reflect the updated image
            frame.repaint();
        }
    }
}
