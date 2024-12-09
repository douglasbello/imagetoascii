package com.douglasbello.io.impl;

import com.douglasbello.io.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class JPEG implements Image {
    private BufferedImage image;

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

        return this.getWidth();
    }

    @Override
    public int getWidth() throws Exception {
        if (this.image == null) {
            throw new Exception("image not loaded yet");
        }

        return this.getHeight();
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
}