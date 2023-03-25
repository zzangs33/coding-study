package com.coding.y2021.april.second;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Zzangs33 implements Heap {
    public static void main(String[] args) {
        Zzangs33 zzangs33 = new Zzangs33();
        System.out.println(zzangs33.diskController(new int[][]{{0, 3}, {1, 9}, {2, 6}}));
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
        Queue<Job> beforeStart = new PriorityQueue<>(Comparator.comparingInt(o -> o.start));
        for (int[] job : jobs) {
            Job cur = new Job(job[0], job[1]);
            beforeStart.add(cur);
        }

        Queue<Job> waiting = new PriorityQueue<>(Comparator.comparingInt(o -> o.length));
        Queue<Job> finished = new LinkedList<>();

        int time = 0;
        while (!(beforeStart.isEmpty() && waiting.isEmpty())) {
            if (!beforeStart.isEmpty()) {
                Job beforeFirst = beforeStart.peek();
                if (time <= beforeFirst.start && waiting.isEmpty()) time = beforeFirst.start;

                while (!beforeStart.isEmpty() && beforeStart.peek().start <= time) {
                    waiting.add(beforeStart.remove());
                }
            }


            Job waitingFirst = waiting.remove();
            time += waitingFirst.length;
            waitingFirst.finish = time;

            finished.add(waitingFirst);
        }

        return (int) finished.stream().mapToInt(j -> j.finish - j.start).average().getAsDouble();
    }

    private static class Job {
        int start;
        int length;
        int finish;

        Job(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        Queue<Integer> maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
        Queue<Integer> minQueue = new PriorityQueue<>();

        for (String operation : operations) {
            char op = operation.charAt(0);
            int val = Integer.parseInt(operation.substring(operation.indexOf(' ') + 1));

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
