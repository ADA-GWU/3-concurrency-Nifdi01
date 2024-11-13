
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ImageUtils {

    public static BufferedImage loadImage(String filePath) {
        try {
            File inputFile = new File(filePath);
            BufferedImage orgImage;
            orgImage = ImageIO.read(inputFile);

            // Resize the image
            return resizeImage(orgImage);
        } catch (IOException e) {
            return null;
        }
    }

    public static BufferedImage copyImage(BufferedImage bi) {
        BufferedImage copImg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        Graphics g = copImg.createGraphics();
        ((Graphics2D) g).drawRenderedImage(bi, null);
        g.dispose();
        return copImg;
    }

    private static BufferedImage resizeImage(BufferedImage orgImage) {
        // Get screen dimensions for resizing the image
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) dim.getWidth();
        int maxHeight = (int) dim.getHeight();

        // Getting the height and width for resizing
        int nWidth = orgImage.getWidth();
        int nHeight = orgImage.getHeight();
        if (orgImage.getWidth() > maxWidth) {
            nWidth = maxWidth;
            nHeight = (nWidth * orgImage.getHeight()) / orgImage.getWidth();
        }
        if (nHeight > maxHeight) {
            nHeight = maxHeight;
            nWidth = (nHeight * orgImage.getWidth()) / orgImage.getHeight();
        }

        // Resize the image
        Image resImage = orgImage.getScaledInstance(nWidth, nHeight, Image.SCALE_DEFAULT);
        BufferedImage resizedBufferedImage = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = resizedBufferedImage.getGraphics();
        g.drawImage(resImage, 0, 0, null);
        g.dispose();

        return resizedBufferedImage;
    }

    public static void saveImage(BufferedImage image) {
        try {
            File outputFile = new File("result.jpg");
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}