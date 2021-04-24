package com.coding.april.third;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Zzangs33 implements Sorting {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();

        System.out.println(Arrays.toString(zzangs33.kthNumber(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}})));

    }


    @Override
    public int[] kthNumber(int[] array, int[][] commands) {
        List<Integer> list = new ArrayList<>();
        List<Integer> res = new ArrayList<>();

        for (int num : array) list.add(num);

        for (int[] cmd : commands) {
            int start = cmd[0];
            int end = cmd[1];
            int k = cmd[2];

            List<Integer> split = new ArrayList<>(list.subList(start - 1, end));
            split.sort(Comparator.naturalOrder());
            res.add(split.get(k - 1));
        }
        return res.stream().mapToInt(num -> num).toArray();
    }

    @Override
    public String theBiggestNumber(int[] numbers) {
        return null;
    }

    @Override
    public int hIndex(int[] citations) {
        return 0;
    }
}
