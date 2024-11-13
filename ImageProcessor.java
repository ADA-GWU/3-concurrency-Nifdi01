
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import javax.imageio.ImageIO;

class ImageProcessor {

    public static void singleThreadProcessing(BufferedImage image, int squareSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y += squareSize) {
            for (int x = 0; x < width; x += squareSize) {
                processSquare(image, x, y, squareSize, width, height);
                DisplayFrame.displayFrame(image);

                // Add a delay to see the progress
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ImageUtils.saveImage(image);
    }

    public static void multiThreadProcessing(BufferedImage image, int squareSize) throws InterruptedException {
        int width = image.getWidth();
        int height = image.getHeight();
        int numCores = Runtime.getRuntime().availableProcessors();
        int colsPerCore = width / numCores; // Divide the width by the number of cores
    
        CompletionService<Void> completionService = new ExecutorCompletionService<>(Main.executorService);
    
        for (int core = 0; core < numCores; core++) {
            final int coreIndex = core;
    
            completionService.submit(() -> {
                // Calculate the x-coordinates range for this core
                int startX = coreIndex * colsPerCore;
                int endX = (coreIndex + 1) * colsPerCore;
                // Ensure the last core takes the remaining width (in case it's not divisible by numCores)
                if (coreIndex == numCores - 1) {
                    endX = width;
                }
    
                for (int x = startX; x < endX; x += squareSize) {
                    for (int y = 0; y < height; y += squareSize) {
                        processSquare(image, x, y, squareSize, width, height);
                        DisplayFrame.displayFrame(image);
    
                        // Add a delay to see the progress
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, null);
        }
    
        // Wait for all tasks to complete
        for (int i = 0; i < numCores; i++) {
            try {
                completionService.take().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        saveImage(image);
    }
    

    private static void processSquare(BufferedImage image, int x, int y, int squareSize, int width, int height) {
        int sWidth = Math.min(squareSize, width - x);
        int sHeight = Math.min(squareSize, height - y);

        int cRed = 0, cGreen = 0, cBlue = 0;
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

        Color avColor = new Color(avgRed, avgGreen, avgBlue);
        for (int sY = y; sY < y + sHeight; sY++) {
            for (int sX = x; sX < x + sWidth; sX++) {
                image.setRGB(sX, sY, avColor.getRGB());
            }
        }
    }

}