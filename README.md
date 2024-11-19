# Image Processor

## Overview

This program is an image processing tool designed to process images using concurrency. It allows for both single-threaded and multi-threaded processing of images by dividing them into smaller square blocks. The program processes each block by averaging the colors within it, and updates the image in real-time. The processed image can be displayed in a graphical user interface (GUI) window, and the final result is stored in memory.

The program utilizes Java’s standard libraries to handle image manipulation, GUI display, and multi-threading. Users can adjust the size of the blocks being processed and see real-time updates of the image as it is being processed.

## Tools Used

The program leverages several Java libraries and techniques:

1. **Java AWT (Abstract Window Toolkit)**:
   - `BufferedImage`: Used for loading and manipulating image data.
   - `Color`: Represents colors and calculates average color values for image blocks.
   - `Graphics`: Handles drawing images on the screen.
   - `Graphics2D`: Extends `Graphics` to support advanced drawing techniques.
   - `Toolkit`: Used to obtain screen dimensions for resizing images.

2. **Java Swing**:
   - `JFrame`: Creates a window to display images.
   - `JLabel`: Displays images in the JFrame window using an `ImageIcon`.

3. **Java Concurrency**:
   - `ExecutorCompletionService`: Manages multi-threaded execution and tracks task completion.
   - `ExecutorService`: Manages a pool of threads for parallel processing of image rows.

4. **Image I/O**:
   - `ImageIO`: Used for loading and saving images in different formats such as JPG.

5. **Java File I/O**:
   - `File`: Manages file operations for reading and saving images.

## Program Structure

The program is composed of the following key classes:

1. **ImageUtils**:
   - This class handles all image-related operations, such as loading an image from a file, saving the processed image, and resizing images to fit the screen dimensions. It also provides a method for copying images.
   
2. **DisplayFrame**:
   - This class is responsible for displaying the image in a GUI window. It creates a JFrame and uses an `ImageIcon` and `JLabel` to display the image. The frame can be updated with the processed image during real-time processing.

3. **ImageProcessor**:
   - This class contains the core logic for image processing. It supports both single-threaded and multi-threaded processing modes.
   - It divides the image into small square blocks and processes each block by calculating the average color of the pixels. The processed image is then updated on the GUI window in real-time.
   - The class supports parallel processing by utilizing the available CPU cores to speed up image manipulation.

4. **Main**
    - Main program for executing the image processor. User inputs the necessary arguments to the command line and an image windows opens that shows the pixelation process.

## Example Usage

To use this program, the user needs to:
1. Load an image file into the program.
2. Adjust the size of the image blocks for processing (referred to as `squareSize`).
3. Choose whether to process the image using a single-threaded or multi-threaded approach (`S` or `M`).
4. Observe real-time updates of the image as it is being processed in a GUI window.
5. Save the final processed image to disk.

```bash
java Main image.jpg 20 M
```

## How It Works

1. **Image Loading**:
   - The program loads an image file using the `ImageUtils.loadImage` method. If the image is successfully loaded, it is passed to the `DisplayFrame.displayFrame` method to display it in a GUI window.

2. **Processing the Image**:
   - The image is divided into smaller square blocks, and each block is processed to calculate the average color of the pixels within it.
   - For single-threaded processing, the program sequentially processes each block of the image.
   - For multi-threaded processing, the program divides the image into rows and processes them in parallel using the available CPU cores. This helps speed up the processing time.

3. **Real-Time Display Updates**:
   - After each block is processed, the image is updated and displayed in the `JFrame` window using the `DisplayFrame.displayFrame` method. A slight delay is introduced to allow users to observe the progress.

4. **Image Saving**:
   - After all processing is complete, the final image is saved using the `ImageUtils.saveImage` method. The processed image is saved in JPG format.

## Example Image

Here is an example of how you might link to an image within your documentation:

<p align="middle">
  <img src="./image.jpg" width="35%" />
  <img src="./result.jpg" width="35%" /> 
</p>


## Conclusion

This program demonstrates the use of image processing techniques combined with Java's multi-threading capabilities for efficient and real-time image manipulation. It is a useful tool for visualizing and processing images in a user-friendly interface, with the ability to scale for multi-core processors.

By modifying the `squareSize` or processing method (single-threaded vs. multi-threaded), users can customize the performance and behavior of the program to fit their specific needs.
