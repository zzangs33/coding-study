package com.coding.april.third;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Zzangs33 implements Sorting {
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
        List<String> list = new ArrayList<>();

        for (int num : numbers) list.add(String.valueOf(num));
        list.sort((a, b) -> Integer.parseInt(a + b) - Integer.parseInt(b + a));

        StringBuilder sb = new StringBuilder();
        for (String num : list) sb.append(num);

        return list.get(0).equals("0") ? "0" : sb.toString();
    }

    @Override
    public int hIndex(int[] citations) {
        List<Integer> list = new ArrayList<>();

        for (int num : citations) list.add(num);
        list.sort(Comparator.reverseOrder());

        if (list.get(0) == 0) return 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < i) return Math.min(list.get(i - 1), i);
        }
        return list.size();
    }
}
