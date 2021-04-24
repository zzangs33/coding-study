package com.coding.april.second;

import java.util.*;

public class Zzangs33 implements Heap {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
        System.out.println(Arrays.toString(zzangs33.dualPriorityQueue(new String[]{"I 16", "D 1"})));
        System.out.println(Arrays.toString(zzangs33.dualPriorityQueue(new String[]{"I 7", "I 5", "I -5", "D -1"})));
        System.out.println(Arrays.toString(zzangs33.dualPriorityQueue(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"})));
        System.out.println(Arrays.toString(zzangs33.dualPriorityQueue(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"})));
    }

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
        Queue<Job> waiting = new PriorityQueue<>((a, b) -> a.length <= b.length ? 1 : -1);
        Queue<Job> orderByInserted = new PriorityQueue<>((a, b) -> a.start <= b.start ? 1 : -1);

        for (int[] job : jobs) {
            Job cur = new Job(job[0], job[1]);
            orderByInserted.add(cur);
        }

        List<Integer> during = new ArrayList<>();
        while (!(orderByInserted.isEmpty() && waiting.isEmpty())) {


        }


        return 0;
    }
    private static class Job {
        int start;
        int length;

        Job(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        Queue<Integer> maxQueue = new PriorityQueue<>();
        Queue<Integer> minQueue = new PriorityQueue<>(Comparator.reverseOrder());

        for (String operation : operations) {
            char op = operation.charAt(0);
            int val = Integer.parseInt(operation.replaceAll("\\D", ""));

            if (op == 'I') {
                maxQueue.add(val);
                minQueue.add(val);
            } else {
                try {
                    if (val == -1) {
                        minQueue.remove();
                        maxQueue.clear();
                        maxQueue.addAll(minQueue);
                    } else {
                        maxQueue.remove();
                        minQueue.clear();
                        minQueue.addAll(maxQueue);
                    }
                } catch (Exception e) {

                }
            }
        }

        try {
            return new int[]{maxQueue.element(), minQueue.element()};
        } catch (Exception e) {
            return new int[]{0, 0};
        }
    }

}
