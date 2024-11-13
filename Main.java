import java.awt.image.BufferedImage;
import java.util.concurrent.*;

/**
 * Main class to process an image using different processing modes.
 * 
 * The program takes three command line arguments:
 * 1. The file path to the image to be processed.
 * 2. The size of the square used for processing the image.
 * 3. The processing mode ('S' for single-threaded or 'M' for multi-threaded).
 */
public class Main {
    // Original image and copied image variables
    private static BufferedImage orgImage;
    private static BufferedImage copyImage;

    // Define the thread pool size based on available processors
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    
    // Executor service for managing the thread pool
    public static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    /**
     * Main entry point of the program.
     * It processes the image based on the given arguments.
     *
     * @param args Command-line arguments where:
     *             - args[0]: File path of the image to be processed
     *             - args[1]: Size of the square used for processing
     *             - args[2]: Processing mode ('S' for single-threaded, 'M' for multi-threaded)
     */
    public static void main(String[] args) throws InterruptedException {
        // Check if the correct number of arguments is provided
        if (args.length < 3) {
            System.out.println("Correct syntax: java Main <image_file_path> <square_size> <thread_type>");
            return;
        }
        
        // Extract file path, square size, and processing mode from the arguments
        String fileName = args[0];
        int squareSize = Integer.parseInt(args[1]);
        char processingMode = args[2].charAt(0);

        // Load the original image
        orgImage = ImageUtils.loadImage(fileName);
        
        // Create a copy of the original image for processing
        copyImage = ImageUtils.copyImage(orgImage);
        
        // Display the copied image in a frame
        DisplayFrame.displayFrame(copyImage);

        // Process the image based on the given processing mode
        if (processingMode == 'S') {
            // Single-threaded processing
            ImageProcessor.singleThreadProcessing(copyImage, squareSize);
        } else if (processingMode == 'M') {
            // Multi-threaded processing
            ImageProcessor.multiThreadProcessing(copyImage, squareSize);
        }
    }
}
