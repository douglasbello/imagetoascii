package com.douglasbello;

import com.douglasbello.io.impl.JPEG;

public class Main {
    public static void main(String[] args) throws Exception {
        JPEG jpeg = new JPEG("C:\\Users\\douglas.bello\\Downloads\\ascii.jpeg");
        int[][] pixels = jpeg.getPixels();

        int[][] brightness = jpeg.pixelsBrightness(pixels);

        for (int[] bright : brightness) {
            for (int color : bright) {
                System.out.println(color);
            }
        }
    }
}