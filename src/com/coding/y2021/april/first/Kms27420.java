package com.coding.y2021.april.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Kms27420 implements QueueNStack {
    public static void main(String[] args) {
        Kms27420 instance = new Kms27420();
//        instance.exe("truckThroughBridge");
//        instance.exe("stockValue");
//        instance.exe("developModule");
        instance.exe("printer");
    }

    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        LinkedList<TruckInfo> queue = new LinkedList<>();
        int i = 0, passCnt = 0, tick = 0, totalWeight = 0, item;
        while (passCnt < truck_weights.length) {
            if (!queue.isEmpty() && queue.get(0).breakPoint == tick) {
                totalWeight -= queue.remove().weight;
                passCnt += 1;
            }
            if (i < truck_weights.length) {
                item = truck_weights[i];
                if (item + totalWeight <= weight) {
                    queue.add(new TruckInfo(item, tick + bridge_length));
                    totalWeight += item;
                    i += 1;
                }
            }
            tick += 1;
        }
        return tick;
    }

    private static final class TruckInfo {
        private final int weight;
        private final int breakPoint;

        private TruckInfo(int weight, int breakPoint) {
            this.weight = weight;
            this.breakPoint = breakPoint;
        }
    }

    @Override
    public int[] stockValue(int[] prices) {
        return this.stockValue(prices, 0);
    }

    private int[] stockValue(int[] prices, int idx) {
        if (idx >= prices.length - 1) return new int[prices.length];
        int[] result = this.stockValue(prices, idx + 1);
        if (prices[idx] > prices[idx + 1]) {
            result[idx] = 1;
            return result;
        }
        int temp = -1;
        for (int i = idx + 1 + result[idx + 1]; i < prices.length; i++)
            if (prices[idx] > prices[i]) {
                temp = i - idx;
                break;
            }
        if (temp == -1)
            temp = prices.length - idx - 1;
        result[idx] = temp;
        return result;
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        List<Integer> result = new ArrayList<>();
        int preDays = Integer.MAX_VALUE, days, rest, speed, cnt = 0;
        for (int i = 0; i < progresses.length; i++) {
            rest = 100 - progresses[i];
            speed = speeds[i];
            days = rest / speed;
            if (rest * speed != days) days += 1;
            if (days <= preDays) {
                if (preDays == Integer.MAX_VALUE)
                    preDays = days;
                cnt += 1;
            } else {
                result.add(cnt);
                preDays = days;
                cnt = 1;
            }
        }
        result.add(cnt);
        return result.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public int printer(int[] priorities, int location) {
        int answer = 0;
        int[] sorted = Arrays.copyOf(priorities, priorities.length);
        Arrays.sort(sorted);
        int popIdx = 0;
        int ckIdx = sorted.length - 1;
        while (answer < priorities.length) {
            if (priorities[popIdx] > 0 && priorities[popIdx] == sorted[ckIdx]) {
                ckIdx--;
                answer++;
                if (popIdx == location) break;
            }
            popIdx = popIdx + 1 >= priorities.length ? 0 : popIdx + 1;
        }
        return answer;
    }
}
