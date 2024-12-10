package com.douglasbello;

import com.douglasbello.io.impl.JPEG;

public class Main {
    public static void main(String[] args) throws Exception {
        JPEG jpeg = new JPEG("C:\\Users\\douglas.bello\\Downloads\\ascii.jpeg");
        int[][] pixels = jpeg.getPixels();

        int[][] brightness = jpeg.pixelsBrightness(pixels);

        String test = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";

        jpeg.draw();
    }
}