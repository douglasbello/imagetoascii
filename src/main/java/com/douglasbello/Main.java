package com.douglasbello;

import com.douglasbello.io.impl.JPEG;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        JPEG jpeg = new JPEG("C:\\Users\\douglas.bello\\Downloads\\ascii.jpeg");
        System.out.println(jpeg.getWidth());

        sc.close();
    }
}