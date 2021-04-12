package com.coding.april.second;

import java.util.PriorityQueue;
import java.util.Queue;

public class Zzangs33 implements Problem {
    @Override
    public int hotter(int[] scoville, int K) {
        Queue<Integer> foods = new PriorityQueue<>();

        for (int food : scoville) foods.add(food);

        int total = 0;
        while (foods.peek() < K) {
            if (foods.size() < 2) return -1;
            int first = foods.remove();
            int second = foods.remove();

            foods.add(first + 2 * second);
            total++;
        }

        return total;
    }

    @Override
    public int diskController(int[][] jobs) {
        return 0;
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        return new int[0];
    }
}
