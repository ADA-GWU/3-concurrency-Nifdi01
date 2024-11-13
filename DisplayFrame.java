
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class DisplayFrame {
    private static JFrame frame;
    
    public static void displayFrame(BufferedImage image) {
        if (frame == null) {
            frame = new JFrame("Image Processing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(image.getWidth(), image.getHeight());

            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);

            frame.setVisible(true);
        } else {
            ((ImageIcon) ((JLabel) frame.getContentPane().getComponent(0)).getIcon()).setImage(image);
            frame.repaint();
        }
    }
}
