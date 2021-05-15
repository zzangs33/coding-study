package com.coding.april.second;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Kms27420 implements Heap {
    public static void main(String[] args) throws IOException {
        Kms27420 instance = new Kms27420();
//        instance.exe("hotter");
//        instance.exe("diskController");
        instance.exe("dualPriorityQueue");
    }

    @Override
    public int hotter(int[] scoville, int K) {
        Queue<Integer> queue = Arrays.stream(scoville)
                .boxed()
                .collect(Collectors.toCollection(PriorityQueue::new));
        int element, cnt = 0;
        while (!queue.isEmpty()) {
            element = queue.remove();
            if (element >= K) return cnt;
            if (queue.isEmpty()) return -1;
            cnt += 1;
            queue.add(element + 2 * queue.remove());
        }
        return -1;
    }

    @Override
    public int diskController(int[][] jobs) {
        Arrays.sort(jobs, Comparator.comparingInt(o -> o[0]));
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        int sum = 0, curTime = 0, i = 0;
        int[] job;
        while (i < jobs.length || !queue.isEmpty()) {
            while (i < jobs.length && jobs[i][0] <= curTime) {
                queue.add(jobs[i++]);
            }
            if (queue.isEmpty())
                curTime = jobs[i][0];
            else {
                job = queue.remove();
                curTime += job[1];
                sum += curTime - job[0];
            }
        }
        return sum / jobs.length;
    }

    @Override
    public int[] dualPriorityQueue(String[] operations) {
        Heap heap = new Heap();
        for (String op : operations) {
            switch (op) {
                case "D 1":
                    heap.popMax();
                    break;
                case "D -1":
                    heap.popMin();
                    break;
                default:
                    heap.push(Integer.parseInt(op.substring(2)));
                    break;
            }
        }
        return new int[]{heap.getMax(), heap.getMin()};
    }

    private static final class Heap {
        private Heap small, big;
        private Integer value;

        private void push(int item) {
            if (this.value == null) this.value = item;
            else if (this.value < item) {
                if (this.big == null) this.big = new Heap();
                this.big.push(item);
            } else {
                if (this.small == null) this.small = new Heap();
                this.small.push(item);
            }
        }

        private Integer pop() {
            if (this.value == null) return null;
            int result = this.value;
            if (this.hasBig()) {
                this.value = this.big.value;
                this.big = this.big.big;
            } else if (this.hasSmall()) {
                this.value = this.small.value;
                this.small = this.small.small;
            } else this.value = null;
            return result;
        }

        private int getMin() {
            if (this.value == null) return 0;
            if (!this.hasSmall()) return this.value;
            return this.small.getMin();
        }

        private int getMax() {
            if (this.value == null) return 0;
            if (!this.hasBig()) return this.value;
            return this.big.getMax();
        }

        private Integer popMin() {
            if (this.value == null) return null;
            if (!this.hasSmall()) return this.pop();
            return this.small.popMin();
        }

        private Integer popMax() {
            if (this.value == null) return null;
            if (!this.hasBig()) return this.pop();
            return this.big.popMax();
        }

        private boolean hasSmall() {
            return this.small != null && this.small.value != null;
        }

        private boolean hasBig() {
            return this.big != null && this.big.value != null;
        }
    }
}
