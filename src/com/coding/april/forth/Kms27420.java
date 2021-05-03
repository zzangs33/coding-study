package com.coding.april.forth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Kms27420 implements BruteForce {
    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Kms27420 instance = new Kms27420();
        instance.exe("virtualTest");
        instance.exe("findPrimeNumber");
        instance.exe("carpet");
    }

    @Override
    public int[] virtualTest(int[] answers) {
        return new int[0];
    }

    @Override
    public int findPrimeNumber(String numbers) {
        return 0;
    }

    @Override
    public int[] carpet(int brown, int yellow) {
        return new int[0];
    }
}
