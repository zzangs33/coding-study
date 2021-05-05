package com.coding.april.first;

import java.util.LinkedList;

public class Kms27420 implements QueueNStack {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
        instance.exe("truckThroughBridge");
//        instance.exe("stockValue");
//        instance.exe("developModule");
//        instance.exe("printer");
    }

    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        LinkedList<Info> queue = new LinkedList<>();
        int i = 0, passCnt = 0, tick = 0, totalWeight = 0, item;
        while (passCnt < truck_weights.length) {
            if (!queue.isEmpty() && queue.get(0).breakPoint == tick) {
                totalWeight -= queue.remove().weight;
                passCnt += 1;
            }
            if (i < truck_weights.length) {
                item = truck_weights[9];
                if (item + totalWeight <= weight) {
                    queue.add(new Info(item, tick + bridge_length));
                    totalWeight += item;
                    i += 1;
                }
            }
            tick += 1;
        }
        return tick;
    }

    private static final class Info {
        private final int weight;
        private final int breakPoint;

        private Info(int weight, int breakPoint) {
            this.weight = weight;
            this.breakPoint = breakPoint;
        }
    }

    @Override
    public int[] stockValue(int[] prices) {
        return new int[0];
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        return new int[0];
    }

    @Override
    public int printer(int[] priorities, int location) {
        return 0;
    }
}
