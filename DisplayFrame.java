
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DisplayFrame {
    private JFrame frame;

    public void createFrame(BufferedImage image){
        frame = new JFrame("Image Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());

        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }

    public void updateImage(BufferedImage image) {
        if(frame != null){
            JLabel label = (JLabel) frame.getContentPane().getComponent(0);
            label.setIcon(new ImageIcon(image));
            frame.repaint();
        }
    }
}
