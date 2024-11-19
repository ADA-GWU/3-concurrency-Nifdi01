import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

/**
 * A utility class for applying processing operations to an image.
 * Supports both single-threaded and multi-threaded processing.
 */
class ImageProcessor {

    /**
     * Processes an image in a single-threaded manner by dividing it into squares
     * and applying average color processing to each square sequentially.
     *
     * @param image      the BufferedImage to process
     * @param squareSize the size of the squares used for processing
     */
    public static void singleThreadProcessing(BufferedImage image, int squareSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Iterate over the image in square blocks
        for (int y = 0; y < height; y += squareSize) {
            for (int x = 0; x < width; x += squareSize) {
                // Process each square block
                processSquare(image, x, y, squareSize, width, height);
                DisplayFrame.displayFrame(image); // Update the display frame

                // Add a short delay to visualize progress
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Save the processed image to disk
        ImageUtils.saveImage(image);
    }

    /**
     * Processes an image using multiple threads by dividing it into rows
     * processed in parallel. Each thread handles a portion of the image.
     *
     * @param image      the BufferedImage to process
     * @param squareSize the size of the squares used for processing
     * @throws InterruptedException if the thread is interrupted while waiting for tasks to complete
     */
    public static void multiThreadProcessing(BufferedImage image, int squareSize) throws InterruptedException {
        int width = image.getWidth();
        int height = image.getHeight();
        int numCores = Runtime.getRuntime().availableProcessors();
        int rowsPerCore = height / numCores; // Divide the height among available cores

        CompletionService<Void> completionService = new ExecutorCompletionService<>(Main.executorService);

        // Submit processing tasks for each core
        for (int core = 0; core < numCores; core++) {
            final int coreIndex = core;

            completionService.submit(() -> {
                int startY = coreIndex * rowsPerCore;
                int endY = (coreIndex + 1) * rowsPerCore;

                // Ensure the last core processes the remaining rows
                if (coreIndex == numCores - 1) {
                    endY = height;
                }

                // Process squares within the assigned row range
                for (int y = startY; y < endY; y += squareSize) {
                    for (int x = 0; x < width; x += squareSize) {
                        processSquare(image, x, y, squareSize, width, height);
                        DisplayFrame.displayFrame(image); // Update the display frame

                        // Add a short delay to visualize progress
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            });
        }

        // Wait for all tasks to complete
        for (int i = 0; i < numCores; i++) {
            try {
                completionService.take().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Save the processed image to disk
        ImageUtils.saveImage(image);
    }

    /**
     * Processes a single square block of the image by averaging its pixel colors
     * and applying the average color to all pixels within the block.
     *
     * @param image      the BufferedImage to process
     * @param x          the starting x-coordinate of the square
     * @param y          the starting y-coordinate of the square
     * @param squareSize the size of the square
     * @param width      the width of the image
     * @param height     the height of the image
     */
    private static void processSquare(BufferedImage image, int x, int y, int squareSize, int width, int height) {
        // Determine the actual size of the square (may be smaller at edges)
        int sWidth = Math.min(squareSize, width - x);
        int sHeight = Math.min(squareSize, height - y);

        int cRed = 0, cGreen = 0, cBlue = 0;

        // Calculate the average color of the square
        for (int sY = y; sY < y + sHeight; sY++) {
            for (int sX = x; sX < x + sWidth; sX++) {
                Color pixelColor = new Color(image.getRGB(sX, sY));
                cRed += pixelColor.getRed();
                cGreen += pixelColor.getGreen();
                cBlue += pixelColor.getBlue();
            }
        }
        int nPixels = sWidth * sHeight;
        int avgRed = cRed / nPixels;
        int avgGreen = cGreen / nPixels;
        int avgBlue = cBlue / nPixels;

        // Create the average color and apply it to all pixels in the square
        Color avColor = new Color(avgRed, avgGreen, avgBlue);
        for (int sY = y; sY < y + sHeight; sY++) {
            for (int sX = x; sX < x + sWidth; sX++) {
                image.setRGB(sX, sY, avColor.getRGB());
            }
        }
    }
}
