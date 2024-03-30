package org.example;
import org.jblas.DoubleMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureProcessor {

    public static double[] process(String inputPath) {

        double[][] imageBrightness = new double[0][];

        try {
            //System.out.println("image found");
            // Read the image from file
            BufferedImage image = ImageIO.read(new File(inputPath));

            // Get dimensions of the image
             int width = image.getWidth();
             int height = image.getHeight();

             imageBrightness = new double[width][height];

            // Process each pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get pixel value
                    int rgb = image.getRGB(x, y);

                    // Extract brightness (average of RGB values)
                    double brightness = (int) (0.2126 * ((rgb >> 16) & 0xFF) +
                            0.7152 * ((rgb >> 8) & 0xFF) +
                            0.0722 * (rgb & 0xFF));

                    // Do something with the brightness value
                    //
                    //System.out.print(brightness + " ");
                    imageBrightness[y][x] = brightness/255.0;
                }
                //System.out.println(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  matrixToVector(new DoubleMatrix(imageBrightness));
    }
    public static double[] matrixToVector(DoubleMatrix doubleMatrix) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < doubleMatrix.rows; i++) {
            for (int j = 0; j < doubleMatrix.columns; j++) {
                list.add(doubleMatrix.get(i, j));
            }
        }
        Double[] buffer =  list.toArray(new Double[0]);
        return toPrimitive(buffer);
    }

    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        }
        int length = array.length;
        double[] primitiveArray = new double[length];
        for (int i = 0; i < length; i++) {
            primitiveArray[i] = array[i]; // Unboxing
        }
        return primitiveArray;
    }


}

