package com.douglasbello.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Image {
    BufferedImage read(String filePath) throws IOException;
    int getHeight() throws Exception;
    int getWidth() throws Exception;
}