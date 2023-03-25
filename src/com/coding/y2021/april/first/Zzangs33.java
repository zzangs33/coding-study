package com.coding.y2021.april.first;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Zzangs33 implements QueueNStack {
    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> onBridge = new LinkedList<>();

        int time = 0;
        int curWeight = 0;

        for (int i = 0; i < bridge_length; i++) onBridge.add(0);

        for (int i = 0; i < truck_weights.length; i++) {
            time++;

            int truck = truck_weights[i];
            curWeight -= onBridge.remove();

            if (curWeight + truck <= weight) {
                onBridge.add(truck);
                curWeight += truck;
            } else {
                onBridge.add(0);
                i--;
            }
        }

        return onBridge.size() + time;
    }

    //TODO: Use stack.
    @Override
    public int[] stockValue(int[] prices) {
        int[] answer = new int[prices.length];

        for (int i = 0; i < prices.length; i++) {
            for (int j = i; j < prices.length; j++) {
                answer[i] = j - i;
                if (prices[i] > prices[j]) {
                    break;
                }
            }
        }

        return answer;
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        int[] answer = new int[100];
        Queue<Integer> leftDays = new LinkedList<>();

        for (int i = 0; i < progresses.length; i++) {
            int leftDay = 100 - progresses[i];
            if (leftDay % speeds[i] == 0) leftDay /= speeds[i];
            else {
                leftDay /= speeds[i];
                leftDay++;
            }

            leftDays.add(leftDay);
        }

        int passedDay = 0;
        while (!leftDays.isEmpty()) {
            int first = leftDays.peek();
            if (first <= passedDay + 1) {
                leftDays.remove();
                answer[passedDay]++;
            } else {
                passedDay++;
            }
        }

        return Arrays.stream(answer).filter(val -> val != 0).toArray();
    }

    @Override
    public int printer(int[] priorities, int location) {
        Queue<Document> waiting = new LinkedList<>();

        for (int i = 0; i < priorities.length; i++) {
            Document doc = new Document();
            doc.priority = priorities[i];
            if (i == location) doc.selected = true;

            waiting.add(doc);
        }

        while (!waiting.isEmpty()) {
            int cur = waiting.peek().priority;
            boolean isMax = true;

            for (Document after : waiting) {
                if (after.priority > cur) {
                    waiting.add(waiting.remove());
                    isMax = false;
                    break;
                }
            }

            if (isMax) {
                Document max = waiting.remove();
                if (max.selected) return priorities.length - waiting.size();
            }
        }
        return priorities.length;
    }

    private static class Document {
        int priority;
        boolean selected;
    }
}
