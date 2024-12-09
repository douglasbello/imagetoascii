package com.douglasbello.io.impl;

import com.douglasbello.io.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
}