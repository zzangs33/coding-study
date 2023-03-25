package com.coding.y2021.april.third;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kms27420 implements Sorting {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
//        instance.exe("kthNumber");
        instance.exe("theBiggestNumber");
//        instance.exe("hIndex");
    }

    @Override
    public int[] kthNumber(int[] array, int[][] commands) {
        int[] result = new int[commands.length];
        int i = 0;
        for (int[] command : commands) {
            int[] copy = Arrays.copyOfRange(array, command[0] - 1, command[1]);
            Arrays.sort(copy);
            result[i++] = copy[command[2] - 1];
        }
        return result;
    }

    @Override
    public String theBiggestNumber(int[] numbers) {
        Queue<String> queue = new PriorityQueue<>((s1, s2) -> {
            int i1 = 0, i2 = 0;
            int comp;
            while (i1 < s1.length() || i2 < s2.length()) {
                if (i1 >= s1.length()) i1 = 0;
                else if (i2 >= s2.length()) i2 = 0;
                comp = s2.charAt(i2++) - s1.charAt(i1++);
                if (comp != 0) return comp;
            }
            return 0;
        });
        boolean isAllZero = true;
        for (int number : numbers) {
            if (number != 0) isAllZero = false;
            queue.add(String.valueOf(number));
        }
        if (isAllZero) return "0";
        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) result.append(queue.remove());
        return result.toString();
    }

    @Override
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int item, prevIdx = -1;
        int i = citations.length;
        while (--i >= 0) {
            item = citations[i];
            if (i == 0 || item != citations[i - 1]) {
                if (prevIdx > -1) {
                    int prev = citations[prevIdx];
                    int prevCnt = citations.length - prevIdx;
                    while (--prev > item)
                        if (prevCnt >= prev) return prev;
                }
                if (citations.length - i >= item) return item;
                prevIdx = i;
            }
        }
        return citations.length;
    }
}
