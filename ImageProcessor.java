
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageProcessor {
    private final ExecutorService executorService;

    public ImageProcessor() {
        int threadPoolSize = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    private Color calculateAverageColor(BufferedImage image, int x, int y, int width, int height){
        int cRed = 0, cGreen=0, cBlue=0;
        int nPixels = width * height;
        
        for(int sY=y; sY<y+height; sY++){
            for(int sX=x; sX<x+width; sX++){
                Color pixelColor = new Color(image.getRGB(sX, sY));
                cRed += pixelColor.getRed();
                cGreen += pixelColor.getGreen();
                cBlue += pixelColor.getBlue();
            }
        }

        return new Color(cRed/nPixels, cGreen/nPixels, cBlue/nPixels);
    }

    private void applyAverageColor(BufferedImage image, int x, int y, int squareSize) {
        int sWidth = Math.min(squareSize, image.getWidth() - x);
        int sHeight = Math.min(squareSize, image.getHeight() - y);
        Color averageColor = calculateAverageColor(image, x, y, sWidth, sHeight);

        for (int sY=y; sY<y+sHeight; sY++){
            for(int sX=x; sX<x+sWidth; sX++){
                image.setRGB(sX, sY, averageColor.getRGB());
            }
        }
    }

    private void delay(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void SingleThreadProcess(BufferedImage image, int squareSize, DisplayFrame displayFrame){
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y += squareSize){
            for (int x = 0; x < width; x += squareSize){
                applyAverageColor(image, x, y, squareSize);
                displayFrame.updateImage(image);
                delay(10);
            }
        }
        ImageUtils.saveImage(image, "result.jpg");
    }
}
