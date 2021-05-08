package com.coding.april.first;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Victory implements QueueNStack {

    public static void main(String[] args) {
        Victory vic = new Victory();
//        vic.exe("developModule");
        vic.exe("stockValue");
//        System.out.println(Arrays.toString(vic.developModule()))
//        System.out.println((int) Math.ceil(((10-3)/ 3f)));
    }

    @Override
    public int truckThroughBridge(int bridge_length, int weight, int[] truck_weights) {
        int sec = 0;
        List<Integer> passedTrucks = new ArrayList<>();
        Queue<Integer> bridgeQ = new LinkedBlockingQueue<>(
                Arrays.stream(new int[bridge_length]).boxed().collect(Collectors.toList()));
        List<Integer> trucksList = Arrays.stream(truck_weights).boxed().collect(Collectors.toList());

        int totalWeight = 0;

        while(passedTrucks.size() < truck_weights.length) {
            if(bridgeQ.peek() == 0) {
                bridgeQ.remove();
            } else {
                totalWeight = totalWeight - bridgeQ.peek();
                passedTrucks.add(bridgeQ.poll());
            }

            if(trucksList.size() != 0) {
                if(weight >= totalWeight + trucksList.get(0)) {
                    bridgeQ.offer(trucksList.get(0));
                    totalWeight += trucksList.remove(0);
                } else {
                    bridgeQ.offer(0);
                }
            }

            sec++;
        } // while
        return sec;
    }

    @Override
    public int[] stockValue(int[] prices) {
        int length = prices.length;

        Queue<Integer> answer = new LinkedList<>();

        for(int i=0; i<length-1;i++) {
            Integer cnt = 0;

            for(int j=i+1; j<length;j++) {
                if(prices[i] <= prices[j]) {
                    cnt++;
                    if(j == length-1) {
                        answer.add(cnt);
                        break;
                    }
                } else {
                    cnt++;
                    answer.add(cnt);
                    break;
                }
            } // inner
        } // outer

        if(answer.size() != length) answer.add(0);
        return answer.stream().mapToInt(i-> i).toArray();
    }

    @Override
    public int[] developModule(int[] progresses, int[] speeds) {
        Queue<Integer> schedulesQ = new LinkedList<>();
        List<Integer> resultList = new ArrayList<>();
        for(int i=0; i < progresses.length; i++) {
            schedulesQ.add((int) Math.ceil((100-progresses[i]) / (float) speeds[i]));
        }

        int count = 1;
        Integer first = schedulesQ.poll();
        while(!schedulesQ.isEmpty()) {
            Integer theNext = schedulesQ.peek();
            if(first >= theNext) {
                count += 1;
                schedulesQ.remove();
            } else {
                resultList.add(count);
                first = schedulesQ.poll();
                count = 1;
            }
        }
        resultList.add(count);

        return resultList.stream().mapToInt(i->i).toArray();
    }

    @Override
    public int printer(int[] priorities, int location) {
        int answer = 0;
        Queue<Integer> q = new LinkedBlockingQueue<>(
                Arrays.stream(priorities).boxed().collect(Collectors.toList()));

        while(!q.isEmpty()) {
            int max = 0;
            for(int item : q) {
                if(max <item) max = item;
            }
            if(q.peek() != max) {
                q.offer(q.poll());
                if(location == 0) location = q.size() -1;
                else location--;
            } else {
                if(location == 0) {
                    answer++;
                    break;
                } else {
                    q.remove();
                    answer++;
                    location--;

                }
            }
        } //while
        return answer;
    }
}
