package com.happydriving.rockets.server.utils;

import java.util.Random;

/**
 * @author mazhiqiang
 */
public class CommonTools {

    public static String generateRandomCode() {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += array[i];
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(CommonTools.generateRandomCode());
        System.out.println(CommonTools.generateRandomCode());
        System.out.println(CommonTools.generateRandomCode());
        System.out.println(CommonTools.generateRandomCode());
    }
}
