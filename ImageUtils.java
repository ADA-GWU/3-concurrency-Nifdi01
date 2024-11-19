import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A utility class for common image processing tasks, such as loading, copying, 
 * resizing, and saving images.
 */
public class ImageUtils {

    /**
     * Loads an image from the specified file path, resizes it to fit the screen
     * dimensions if necessary, and returns the resized image.
     *
     * @param filePath the path of the image file to load
     * @return a resized BufferedImage, or null if an error occurs during loading
     */
    public static BufferedImage loadImage(String filePath) {
        try {
            File inputFile = new File(filePath);
            BufferedImage orgImage = ImageIO.read(inputFile);

            // Resize the image to fit within screen dimensions
            return resizeImage(orgImage);
        } catch (IOException e) {
            // Return null if the image file cannot be loaded
            return null;
        }
    }

    /**
     * Creates a copy of the given BufferedImage.
     *
     * @param bi the BufferedImage to copy
     * @return a new BufferedImage that is an exact copy of the input image
     */
    public static BufferedImage copyImage(BufferedImage bi) {
        BufferedImage copImg = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        Graphics g = copImg.createGraphics();

        // Render the original image onto the new image
        ((Graphics2D) g).drawRenderedImage(bi, null);
        g.dispose();
        return copImg;
    }

    /**
     * Resizes the given BufferedImage to fit within the screen dimensions while 
     * maintaining the aspect ratio.
     *
     * @param orgImage the original BufferedImage to resize
     * @return a resized BufferedImage
     */
    private static BufferedImage resizeImage(BufferedImage orgImage) {
        // Get screen dimensions to determine the maximum allowed size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) dim.getWidth();
        int maxHeight = (int) dim.getHeight();

        // Calculate new dimensions maintaining the aspect ratio
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

        // Resize the image using scaled instance
        Image resImage = orgImage.getScaledInstance(nWidth, nHeight, Image.SCALE_DEFAULT);
        BufferedImage resizedBufferedImage = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_RGB);

        // Draw the resized image onto a new BufferedImage
        Graphics g = resizedBufferedImage.getGraphics();
        g.drawImage(resImage, 0, 0, null);
        g.dispose();

        return resizedBufferedImage;
    }

    /**
     * Saves the given BufferedImage as a JPG file named "result.jpg" in the 
     * current working directory.
     *
     * @param image the BufferedImage to save
     */
    public static void saveImage(BufferedImage image) {
        try {
            File outputFile = new File("result.jpg");

            // Write the image to the file in JPEG format
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException e) {
            // Print the stack trace if an error occurs during saving
            e.printStackTrace();
        }
    }
}
