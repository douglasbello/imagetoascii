package com.douglasbello.io.impl;

import com.douglasbello.io.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class JPEG implements Image {
    private BufferedImage image;
    private static final String ASCII = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

    public JPEG(String filePath) throws IOException {
        read(filePath);
    }

    @Override
    public BufferedImage read(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        this.image = image;

        return image;
    }

    @Override
    public int getHeight() throws Exception {
        if (this.image == null) {
            throw new Exception("image not loaded yet");
        }

        return this.image.getHeight();
    }

    @Override
    public int getWidth() throws Exception {
        if (this.image == null) {
            throw new Exception("image not loaded yet");
        }

        return this.image.getWidth();
    }

    @Override
    public int[][] getPixels() throws Exception {
        if (this.image == null) {
            throw new Exception("image not loaded yet");
        }

        byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        int height = getHeight();
        int width = getWidth();
        boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            int numberOfValues = 4;
            for (int valueIndex = 0, row = 0, col = 0; valueIndex + numberOfValues - 1 < pixelData.length; valueIndex += numberOfValues) {
                int argb = 0;
                argb += (((int) pixelData[valueIndex] & 0xff) << 24);
                argb += ((int) pixelData[valueIndex + 1] & 0xff);
                argb += (((int) pixelData[valueIndex + 2] & 0xff) << 8);
                argb += (((int) pixelData[valueIndex + 3] & 0xff) << 16);
                result[row][col] = argb;

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            int numberOfValues = 3;
            for (int valueIndex = 0, row = 0, col = 0; valueIndex + numberOfValues - 1 < pixelData.length; valueIndex += numberOfValues) {
                int argb = 0;
                argb += -16777216;
                argb += ((int) pixelData[valueIndex] & 0xff);
                argb += (((int) pixelData[valueIndex + 1] & 0xff) << 8);
                argb += (((int) pixelData[valueIndex + 2] & 0xff) << 16);
                result[row][col] = argb;

                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

    @Override
    public int[][] pixelsBrightness(int[][] pixels) throws Exception {
        int[][] brightness = new int[pixels.length][pixels.length];

        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                int argb = pixels[row][col];

                int alpha = (argb >> 24) & 0xFF;
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;

                int[] color = {alpha, red, green, blue};
                brightness[row][col] = pixelAverage(color);
            }
        }

        return brightness;
    }

    @Override
    public int pixelAverage(int[] rgb) throws Exception {
        return (rgb[1] + rgb[2] + rgb[3]) / 3;
    }

    @Override
    public char getCharByBrightness(int brightness) {
        int index = brightness / this.ASCII.length();
        return this.ASCII.charAt(index);
    }

    @Override
    public void draw() throws Exception {
        int[][] pixels = getPixels();
        int[][] brightness = pixelsBrightness(pixels);

        for (int row = 0; row < brightness.length; row++) {
            for (int col = 0; col < brightness[row].length; col++) {
                System.out.print(getCharByBrightness(brightness[row][col]));
            }
            System.out.println();
        }
    }
}